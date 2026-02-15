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
import uninabiogarden.entities.Progetto;

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
    var progettiQuerySql = """
          SELECT *
          FROM progetto
          WHERE id_proprietario = ?
        """;

    var coltivazioniQuerySql = """
          SELECT *
          FROM coltivazione
          WHERE id_progetto = ?
        """;

    var coltivatoriQuerySql = """
          SELECT id
          FROM utente
          JOIN lavora_per AS lp ON utente.id = lp.id_coltivatore
          WHERE lp.id_progetto = ?
        """;

    List<Progetto> progetti = new ArrayList<>();

    try (Connection conn = database.getConnection();
        PreparedStatement pstmtQueryProgetti = conn.prepareStatement(progettiQuerySql);
        PreparedStatement pstmtQueryColtivazioni = conn.prepareStatement(coltivazioniQuerySql);
        PreparedStatement pstmtQueryColtivatori = conn.prepareStatement(coltivatoriQuerySql)) {
      pstmtQueryProgetti.setLong(1, proprietarioId);
      ResultSet rs = pstmtQueryProgetti.executeQuery();
      while (rs.next()) {
        Progetto progetto = new Progetto();
        progetto.setId(rs.getLong("id"));
        progetto.setNomeProgetto(rs.getString("nome"));
        progetto.setDescrizione(rs.getString("descrizione"));
        progetto.setStato(Progetto.Stato.valueOf(rs.getString("stato")));

        var dataInizioProgetto = rs.getDate("data_inizio");
        progetto.setDataInizio(dataInizioProgetto != null ? dataInizioProgetto.toLocalDate() : null);

        var dataFineProgetto = rs.getDate("data_fine");
        progetto.setDataFine(dataFineProgetto != null ? dataFineProgetto.toLocalDate() : null);

        // il proprietario sarebbe l'utente loggato
        // per il lotto si usa un proxy (assumendo che i lotti del proprietario siano
        // gia in memoria)
        var lottoProxy = new Lotto();
        lottoProxy.setId(rs.getLong("id_lotto"));
        progetto.setLotto(lottoProxy);

        // recupero delle coltivazioni associate al progetto
        List<Coltivazione> coltivazioni = new ArrayList<>();
        pstmtQueryColtivazioni.setLong(1, progetto.getId());
        ResultSet rsColtivazioni = pstmtQueryColtivazioni.executeQuery();
        while (rsColtivazioni.next()) {
          Coltivazione coltivazione = new Coltivazione();
          coltivazione.setId(rsColtivazioni.getLong("id"));
          coltivazione
              .setStatoSalute(Coltivazione.StatoSaluteColtivazione.valueOf(rsColtivazioni.getString("stato_salute")));
          coltivazione.setStato(Coltivazione.StatoColtivazione.valueOf(rsColtivazioni.getString("stato")));

          var dataInizio = rsColtivazioni.getDate("data_inizio");
          coltivazione.setDataInizio(dataInizio != null ? dataInizio.toLocalDate() : null);

          coltivazione.setNoteTecniche(rsColtivazioni.getString("note_tecniche"));

          // per la coltura si usa un proxy (assumendo che le colture siano gia in
          // memoria)
          var colturaProxy = new Coltura();
          colturaProxy.setId(rsColtivazioni.getLong("id_coltura"));
          coltivazione.setColtura(colturaProxy);
          coltivazioni.add(coltivazione);
        }
        progetto.setColtivazioni(coltivazioni);

        // recupero dei coltivatori associati al progetto
        List<Coltivatore> coltivatori = new ArrayList<>();
        pstmtQueryColtivatori.setLong(1, progetto.getId());
        ResultSet rsColtivatori = pstmtQueryColtivatori.executeQuery();
        while (rsColtivatori.next()) {
          // per i coltivatori si usano proxy (assumendo che i coltivatori del progetto
          // siano gia in memoria)
          Coltivatore coltivatoreProxy = new Coltivatore();
          coltivatoreProxy.setId(rsColtivatori.getLong("id"));
          coltivatori.add(coltivatoreProxy);
        }
        progetto.setColtivatori(coltivatori);
        progetti.add(progetto);
      }
    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei progetti: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero dei progetti. Riprova più tardi.");
    }

    return progetti;
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

}
