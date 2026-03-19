package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import uninabiogarden.Utils;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Coltura;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Orto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;

public class ProgettoDao {

  private Database database = Database.getInstance();

  public Progetto saveProgetto(Progetto progetto) {
    Connection conn = null;
    try {
      conn = database.getConnection();
      conn.setAutoCommit(false); // Inizia la transazione (tutte le operazioni saranno atomiche)

      // 1. Inserisci il progetto e ottieni l'ID generato
      String insertProgettoSql = "INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES (?, ?, ?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertProgettoSql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, progetto.getNomeProgetto());
        pstmt.setString(2, progetto.getDescrizione());
        pstmt.setLong(3, progetto.getProprietario().getId());
        pstmt.setLong(4, progetto.getLotto().getId());
        pstmt.executeUpdate();

        // Recupera l'ID generato
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          progetto.setId(rs.getLong(1));
        }
        rs.close();
      } catch (Exception e) {
        System.err.println("Errore durante l'inserimento del progetto: " + e.getMessage());
        throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
      }

      // Recupera i campi generati automaticamente dal database
      String selectProgettoSql = "SELECT stato, data_inizio, data_fine FROM progetto WHERE id = ?";
      try (PreparedStatement pstmt = conn.prepareStatement(selectProgettoSql)) {
        pstmt.setLong(1, progetto.getId());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          progetto.setStato(Progetto.Stato.valueOf(rs.getString("stato")));

          var dataInizio = rs.getDate("data_inizio");
          progetto.setDataInizio(dataInizio != null ? dataInizio.toLocalDate() : null);

          var dataFine = rs.getDate("data_fine");
          progetto.setDataFine(dataFine != null ? dataFine.toLocalDate() : null);
        }
      } catch (Exception e) {
        System.err.println("Errore durante il recupero dei dati del progetto: " + e.getMessage());
        throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
      }

      // 2. Inserimento delle coltivazioni in batch per performance
      String insertColtivazioneSql = "INSERT INTO coltivazione (note_tecniche, id_progetto, id_coltura) VALUES (?, ?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertColtivazioneSql)) {
        for (Coltivazione coltivazione : progetto.getColtivazioni()) {
          pstmt.setString(1, coltivazione.getNoteTecniche());
          pstmt.setLong(2, progetto.getId());
          pstmt.setLong(3, coltivazione.getColtura().getId());
          pstmt.addBatch();
        }
        pstmt.executeBatch();
      } catch (SQLException e) {
        String errorMessage = Utils.extractSQLErrorMessage(e);
        System.err.println("Errore durante l'inserimento delle coltivazioni: " + errorMessage);
        throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
      }

      // 3. Inserisci le associazioni lavora_per in batch per performance
      String insertLavoraPerSql = "INSERT INTO lavora_per (id_progetto, id_coltivatore) VALUES (?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertLavoraPerSql)) {
        for (Coltivatore coltivatore : progetto.getColtivatori()) {
          pstmt.setLong(1, progetto.getId());
          pstmt.setLong(2, coltivatore.getId());
          pstmt.addBatch();
        }
        pstmt.executeBatch();
      } catch (Exception e) {
        System.err.println("Errore durante l'inserimento delle associazioni lavora_per: " + e.getMessage());
        throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
      }

      conn.commit(); // Commit della transazione
      return progetto;

    } catch (Exception e) {
      if (conn != null) {
        try {
          conn.rollback(); // Esegue il rollback in caso di errore
        } catch (SQLException ex) {
          System.err.println("Errore durante il rollback della transazione: " + ex.getMessage());
        }
      }
      System.err.println("Errore durante la creazione del progetto: " + e.getMessage());
      throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
    } finally {
      if (conn != null) {
        try {
          conn.setAutoCommit(true);
          conn.close();
        } catch (SQLException e) {
          System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
          throw new RuntimeException("Errore durante la creazione del progetto. Riprova più tardi.");
        }
      }
    }
  }

  public List<Progetto> findAll(Long proprietarioId) {
    var sql = """
          SELECT *
          FROM progetto
          WHERE id_proprietario = ?
        """;

    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, proprietarioId);

      ResultSet rs = pstmt.executeQuery();
      List<Progetto> progetti = new ArrayList<>();
      while (rs.next()) {
        Progetto progetto = new Progetto();
        progetto.setId(rs.getLong("id"));
        progetto.setNomeProgetto(rs.getString("nome"));
        progetto.setDescrizione(rs.getString("descrizione"));
        progetto.setStato(Progetto.Stato.valueOf(rs.getString("stato")));
        var dataInizio = rs.getDate("data_inizio");
        progetto.setDataInizio(dataInizio != null ? dataInizio.toLocalDate() : null);
        var dataFine = rs.getDate("data_fine");
        progetto.setDataFine(dataFine != null ? dataFine.toLocalDate() : null);

        // proxy del lotto (gia caricato nel proprietario)
        var lotto = new Lotto();
        lotto.setId(rs.getLong("id_lotto"));
        progetto.setLotto(lotto);

        progetti.add(progetto);
      }
      return progetti;

    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei progetti: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero dei progetti. Riprova più tardi.");
    }
  }

  public void updateProgetto(String nome, String descrizione, Long id) {
    String updateProgettoSql = "UPDATE progetto SET nome = ?, descrizione = ? WHERE id = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(updateProgettoSql)) {
      pstmt.setString(1, nome);
      pstmt.setString(2, descrizione);
      pstmt.setLong(3, id);
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.err.println("Errore durante l'aggiornamento del progetto: " + e.getMessage());
      throw new RuntimeException("Errore durante l'aggiornamento del progetto. Riprova più tardi.");
    }
  }

  public void updateProgetto(String nuovoStato, Long id) {
    String updateProgettoSql = "UPDATE progetto SET stato = ?::stato_progetto WHERE id = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(updateProgettoSql)) {
      pstmt.setString(1, nuovoStato);
      pstmt.setLong(2, id);
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.err.println("Errore durante l'aggiornamento del progetto: " + e.getMessage());
      throw new RuntimeException("Errore durante l'aggiornamento del progetto. Riprova più tardi.");
    }
  }

  public List<Coltivatore> findColtivatoriIds(Long progettoId) {
    var sql = """
          SELECT c.*
          FROM (
            SELECT *
            FROM utente
            WHERE tipo = 'COLTIVATORE'
          ) c
          JOIN lavora_per lp ON c.id = lp.id_coltivatore
          WHERE lp.id_progetto = ?
        """;

    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, progettoId);

      ResultSet rs = pstmt.executeQuery();
      List<Coltivatore> coltivatori = new ArrayList<>();
      while (rs.next()) {
        Coltivatore coltivatore = new Coltivatore();
        coltivatore.setId(rs.getLong("id"));
        coltivatori.add(coltivatore);
      }
      return coltivatori;
    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei coltivatori del progetto: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero dei coltivatori del progetto. Riprova più tardi.");
    }
  }

  public void deleteProgetto(Long progettoId) {
    var sql = "DELETE FROM progetto WHERE id = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, progettoId);
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.err.println("Errore durante l'eliminazione del progetto: " + e.getMessage());
      throw new RuntimeException("Errore durante l'eliminazione del progetto. Riprova più tardi.");
    }
  }

  public List<Progetto> findAllByColtivatoreId(Long coltivatoreId) {
    var sql = """
          SELECT
            p.id            AS progetto_id,
            p.nome          AS progetto_nome,
            p.descrizione   AS progetto_descrizione,
            p.stato         AS progetto_stato,
            p.data_inizio   AS progetto_data_inizio,
            p.data_fine     AS progetto_data_fine,
            l.id            AS lotto_id,
            l.codice_lotto,
            l.estensione_mq,
            l.tipologia_terreno,
            o.id            AS orto_id,
            o.nome_orto,
            o.citta,
            o.cap,
            o.via,
            o.civico,
            prop.id             AS prop_id,
            prop.username       AS prop_username,
            prop.email          AS prop_email,
            prop.nome           AS prop_nome,
            prop.cognome        AS prop_cognome,
            prop.b_day          AS prop_b_day,
            prop.gender         AS prop_gender,
            prop.bio            AS prop_bio,
            prop.codice_fiscale AS prop_codice_fiscale
          FROM progetto p
          JOIN lotto l      ON p.id_lotto = l.id
          JOIN orto o       ON l.id_orto = o.id
          JOIN utente prop  ON p.id_proprietario = prop.id
          JOIN lavora_per lp ON p.id = lp.id_progetto
          WHERE lp.id_coltivatore = ?
        """;

    try (Connection conn = database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, coltivatoreId);

      ResultSet rs = pstmt.executeQuery();
      List<Progetto> progetti = new ArrayList<>();
      while (rs.next()) {
        // Progetto
        Progetto progetto = new Progetto();
        progetto.setId(rs.getLong("progetto_id"));
        progetto.setNomeProgetto(rs.getString("progetto_nome"));
        progetto.setDescrizione(rs.getString("progetto_descrizione"));
        progetto.setStato(Progetto.Stato.valueOf(rs.getString("progetto_stato")));
        var dataInizio = rs.getDate("progetto_data_inizio");
        progetto.setDataInizio(dataInizio != null ? dataInizio.toLocalDate() : null);
        var dataFine = rs.getDate("progetto_data_fine");
        progetto.setDataFine(dataFine != null ? dataFine.toLocalDate() : null);

        // Orto
        var orto = new Orto();
        orto.setId(rs.getLong("orto_id"));
        orto.setNomeOrto(rs.getString("nome_orto"));
        orto.setCitta(rs.getString("citta"));
        orto.setCap(rs.getString("cap"));
        orto.setVia(rs.getString("via"));
        orto.setCivico(rs.getString("civico"));

        // Lotto
        var lotto = new Lotto();
        lotto.setId(rs.getLong("lotto_id"));
        lotto.setCodiceLotto(rs.getString("codice_lotto"));
        lotto.setEstensioneMq(rs.getDouble("estensione_mq"));
        lotto.setTipologiaTerreno(Lotto.TipologiaTerreno.valueOf(rs.getString("tipologia_terreno")));
        lotto.setOrto(orto);

        // Proprietario
        var proprietario = new Proprietario();
        proprietario.setId(rs.getLong("prop_id"));
        proprietario.setUsername(rs.getString("prop_username"));
        proprietario.setEmail(rs.getString("prop_email"));
        proprietario.setNome(rs.getString("prop_nome"));
        proprietario.setCognome(rs.getString("prop_cognome"));
        var bDay = rs.getDate("prop_b_day");
        proprietario.setbDay(bDay != null ? bDay.toLocalDate() : null);
        proprietario.setGender(rs.getString("prop_gender"));
        proprietario.setBio(rs.getString("prop_bio"));
        proprietario.setCodiceFiscale(rs.getString("prop_codice_fiscale"));

        progetto.setLotto(lotto);
        progetto.setProprietario(proprietario);

        progetti.add(progetto);
      }
      return progetti;

    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei progetti del coltivatore: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero dei progetti del coltivatore. Riprova più tardi.");
    }

  }

}
