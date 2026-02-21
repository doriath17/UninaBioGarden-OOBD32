package uninabiogarden.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Concimazione;
import uninabiogarden.entities.Irrigazione;
import uninabiogarden.entities.Raccolta;
import uninabiogarden.entities.Semina;
import uninabiogarden.entities.Trattamento;

public class AttivitaDao {

  private Database database = Database.getInstance();

  public List<Attivita> findByColtivazioneId(Long coltivazioneId) {
    var attivita = new ArrayList<Attivita>();
    attivita.addAll(findByColtivazioneId("irrigazione", coltivazioneId));
    attivita.addAll(findByColtivazioneId("concimazione", coltivazioneId));
    attivita.addAll(findByColtivazioneId("semina", coltivazioneId));
    attivita.addAll(findByColtivazioneId("raccolta", coltivazioneId));
    attivita.addAll(findByColtivazioneId("trattamento", coltivazioneId));
    return attivita;
  }

  private List<Attivita> findByColtivazioneId(String tabellaAttivita, Long coltivazioneId) {
    var sql = String.format("""
        SELECT *
        FROM %s as subtype
        JOIN attivita a ON a.id = subtype.id
        WHERE a.id_coltivazione = ?
        """, tabellaAttivita);

    try (var conn = database.getConnection();
        var stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, coltivazioneId);
      var rs = stmt.executeQuery();

      var attivita = new ArrayList<Attivita>();
      while (rs.next()) {

        Attivita attivitaItem = null;
        switch (tabellaAttivita) {
          case "irrigazione":
            Irrigazione irrigazione = new Irrigazione();
            irrigazione.setMetodo(Irrigazione.MetodoIrrigazione.valueOf(rs.getString("metodo")));
            irrigazione.setVolumeAcquaL(rs.getDouble("volume_acqua_l"));
            attivitaItem = irrigazione;
            break;
          case "concimazione":
            Concimazione concimazione = new Concimazione();
            concimazione.setTipoConcime(Concimazione.TipoConcime.valueOf(rs.getString("tipo_concime")));
            concimazione.setQuantitaKg(rs.getDouble("quantita_kg"));
            attivitaItem = concimazione;
            break;
          case "semina":
            Semina semina = new Semina();
            semina.setQuantitaSementi(rs.getInt("quantita_sementi"));
            semina.setProfonditaSeminaCm(rs.getDouble("profondita_semina_cm"));
            attivitaItem = semina;
            break;
          case "raccolta":
            Raccolta raccolta = new Raccolta();
            raccolta.setQuantitaPrevistaKg(rs.getDouble("quantita_prevista_kg"));
            raccolta.setQuantitaEffettivaKg(rs.getDouble("quantita_effettiva_kg"));
            attivitaItem = raccolta;
            break;
          case "trattamento":
            Trattamento trattamento = new Trattamento();
            trattamento.setNomeProdotto(rs.getString("nome_prodotto"));
            trattamento.setTempoCarenza(rs.getInt("tempo_carenza"));
            attivitaItem = trattamento;
            break;
          default:
            break;
        }

        // attributi della superclasse Attivita
        attivitaItem.setId(rs.getLong("id"));
        attivitaItem.setNome(rs.getString("nome"));
        attivitaItem.setDataPianificazione(rs.getDate("data_pianificazione") != null
            ? rs.getDate("data_pianificazione").toLocalDate()
            : null);
        attivitaItem.setDataInizio(rs.getDate("data_inizio") != null ? rs.getDate("data_inizio").toLocalDate() : null);
        attivitaItem.setDataFine(rs.getDate("data_fine") != null ? rs.getDate("data_fine").toLocalDate() : null);
        attivitaItem
            .setDataScadenza(rs.getDate("data_scadenza") != null ? rs.getDate("data_scadenza").toLocalDate() : null);
        attivitaItem.setStato(Attivita.Stato.valueOf(rs.getString("stato")));
        attivitaItem.setNoteTecniche(rs.getString("note_tecniche"));

        // proxy del coltivatore (gia caricato nel progetto)
        var coltivatore = new Coltivatore();
        coltivatore.setId(rs.getLong("id_coltivatore"));
        attivitaItem.setColtivatore(coltivatore);

        attivita.add(attivitaItem);
      }
      return attivita;
    } catch (Exception e) {
      System.err.println("Errore durante il recupero delle attivita: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero delle attivita. Riprova più tardi.");
    }
  }

  // ==============================================================================================
  // Update Attivita (sia tabella attivita che tabella specifica)
  // ==============================================================================================

  public Attivita update(Attivita dto) {
    var sqlUpdateAttivita = """
        UPDATE attivita
        SET data_inizio = ?, data_scadenza = ?, stato = ?::stato_attivita, note_tecniche = ?
        WHERE id = ?
        RETURNING data_pianificazione, data_inizio, data_fine, data_scadenza, stato, note_tecniche
        """;
    var sqlUpdateSpecifico = getSpecificUpdateSql(dto);

    try (var conn = database.getConnection();
        var stmtAttivita = conn.prepareStatement(sqlUpdateAttivita);
        var stmtSpecifico = conn.prepareStatement(sqlUpdateSpecifico)) {

      updateAttivitaGenerica(dto, stmtAttivita);

      // update tabella specifica
      if (dto instanceof Irrigazione irrigazione) {
        updateIrrigazione(irrigazione, stmtSpecifico);
      } else if (dto instanceof Concimazione concimazione) {
        updateConcimazione(concimazione, stmtSpecifico);
      } else if (dto instanceof Semina semina) {
        updateSemina(semina, stmtSpecifico);
      } else if (dto instanceof Raccolta raccolta) {
        updateRaccolta(raccolta, stmtSpecifico);
      } else if (dto instanceof Trattamento trattamento) {
        updateTrattamento(trattamento, stmtSpecifico);
      }
    } catch (Exception e) {
      System.err.println("Errore durante l'aggiornamento dell'attività: " + e.getMessage());
      throw new RuntimeException("Errore durante l'aggiornamento dell'attività. Riprova più tardi.");
    }
    return dto;
  }

  private String getSpecificUpdateSql(Attivita dto) {
    if (dto instanceof Irrigazione) {
      return """
          UPDATE irrigazione
          SET metodo = ?::t_metodo_irrigazione, volume_acqua_l = ?
          WHERE id = ?
          RETURNING metodo, volume_acqua_l
          """;
    } else if (dto instanceof Concimazione) {
      return """
          UPDATE concimazione
          SET tipo_concime = ?::t_tipo_concime, quantita_kg = ?
          WHERE id = ?
          RETURNING tipo_concime, quantita_kg
          """;
    } else if (dto instanceof Semina) {
      return """
          UPDATE semina
          SET quantita_sementi = ?, profondita_semina_cm = ?
          WHERE id = ?
          RETURNING quantita_sementi, profondita_semina_cm
          """;
    } else if (dto instanceof Raccolta) {
      return """
          UPDATE raccolta
          SET quantita_prevista_kg = ?, quantita_effettiva_kg = ?
          WHERE id = ?
          RETURNING quantita_prevista_kg, quantita_effettiva_kg
          """;
    } else if (dto instanceof Trattamento) {
      return """
          UPDATE trattamento
          SET nome_prodotto = ?, tempo_carenza = ?
          WHERE id = ?
          RETURNING nome_prodotto, tempo_carenza
          """;
    }
    return null;
  }

  private void updateSemina(Semina semina, PreparedStatement stmt) throws Exception {
    stmt.setInt(1, semina.getQuantitaSementi());
    stmt.setObject(2, semina.getProfonditaSeminaCm());
    stmt.setLong(3, semina.getId());
    try (var rs = stmt.executeQuery()) {
      if (rs.next()) {
        semina.setQuantitaSementi(rs.getInt("quantita_sementi"));
        double prof = rs.getDouble("profondita_semina_cm");
        semina.setProfonditaSeminaCm(rs.wasNull() ? null : prof);
      }
    }
  }

  private void updateTrattamento(Trattamento trattamento, PreparedStatement stmt) throws Exception {
    stmt.setString(1, trattamento.getNomeProdotto());
    stmt.setObject(2, trattamento.getTempoCarenza());
    stmt.setLong(3, trattamento.getId());
    try (var rs = stmt.executeQuery()) {
      if (rs.next()) {
        trattamento.setNomeProdotto(rs.getString("nome_prodotto"));
        int car = rs.getInt("tempo_carenza");
        trattamento.setTempoCarenza(rs.wasNull() ? null : car);
      }
    }
  }

  private void updateRaccolta(Raccolta raccolta, PreparedStatement stmt) throws Exception {
    stmt.setObject(1, raccolta.getQuantitaPrevistaKg());
    stmt.setObject(2, raccolta.getQuantitaEffettivaKg());
    stmt.setLong(3, raccolta.getId());
    try (var rs = stmt.executeQuery()) {
      if (rs.next()) {
        double prev = rs.getDouble("quantita_prevista_kg");
        raccolta.setQuantitaPrevistaKg(rs.wasNull() ? null : prev);
        double eff = rs.getDouble("quantita_effettiva_kg");
        raccolta.setQuantitaEffettivaKg(rs.wasNull() ? null : eff);
      }
    }
  }

  private void updateConcimazione(Concimazione concimazione, PreparedStatement stmt) throws Exception {
    stmt.setString(1, concimazione.getTipoConcime() != null ? concimazione.getTipoConcime().name() : null);
    stmt.setObject(2, concimazione.getQuantitaKg());
    stmt.setLong(3, concimazione.getId());
    try (var rs = stmt.executeQuery()) {
      if (rs.next()) {
        String tipo = rs.getString("tipo_concime");
        concimazione.setTipoConcime(tipo != null ? Concimazione.TipoConcime.valueOf(tipo) : null);
        double qty = rs.getDouble("quantita_kg");
        concimazione.setQuantitaKg(rs.wasNull() ? null : qty);
      }
    }
  }

  private void updateIrrigazione(Irrigazione irrigazione, PreparedStatement stmt) throws Exception {
    stmt.setString(1, irrigazione.getMetodo() != null ? irrigazione.getMetodo().name() : null);
    stmt.setObject(2, irrigazione.getVolumeAcquaL());
    stmt.setLong(3, irrigazione.getId());
    try (var rs = stmt.executeQuery()) {
      if (rs.next()) {
        String metodo = rs.getString("metodo");
        irrigazione.setMetodo(metodo != null ? Irrigazione.MetodoIrrigazione.valueOf(metodo) : null);
        double vol = rs.getDouble("volume_acqua_l");
        irrigazione.setVolumeAcquaL(rs.wasNull() ? null : vol);
      }
    }
  }

  private void updateAttivitaGenerica(Attivita dto, PreparedStatement stmt) throws Exception {
    stmt.setDate(1, dto.getDataInizio() != null ? java.sql.Date.valueOf(dto.getDataInizio()) : null);
    stmt.setDate(2, dto.getDataScadenza() != null ? java.sql.Date.valueOf(dto.getDataScadenza()) : null);
    stmt.setString(3, dto.getStato().name());
    stmt.setString(4, dto.getNoteTecniche());
    stmt.setLong(5, dto.getId());

    try (var rsAttivita = stmt.executeQuery()) {
      if (rsAttivita.next()) {
        dto.setDataPianificazione(
            rsAttivita.getDate("data_pianificazione") != null
                ? rsAttivita.getDate("data_pianificazione").toLocalDate()
                : null);
        dto.setDataInizio(
            rsAttivita.getDate("data_inizio") != null
                ? rsAttivita.getDate("data_inizio").toLocalDate()
                : null);
        dto.setDataFine(
            rsAttivita.getDate("data_fine") != null
                ? rsAttivita.getDate("data_fine").toLocalDate()
                : null);
        dto.setDataScadenza(
            rsAttivita.getDate("data_scadenza") != null
                ? rsAttivita.getDate("data_scadenza").toLocalDate()
                : null);
        dto.setStato(Attivita.Stato.valueOf(rsAttivita.getString("stato")));
        dto.setNoteTecniche(rsAttivita.getString("note_tecniche"));
      }
    }
  }

}
