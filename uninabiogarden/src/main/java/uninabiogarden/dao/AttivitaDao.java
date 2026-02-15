package uninabiogarden.dao;

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

}
