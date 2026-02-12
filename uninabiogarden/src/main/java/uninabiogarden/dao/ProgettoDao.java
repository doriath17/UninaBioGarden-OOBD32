package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.chart.PieChart.Data;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Coltura;
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

      // 2. Inserimento delle coltivazioni in batch per performance
      String insertColtivazioneSql = "INSERT INTO coltivazione (quantita_piante, note_tecniche, id_progetto, id_coltura) VALUES (?, ?, ?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertColtivazioneSql)) {
        for (Coltivazione coltivazione : progetto.getColtivazioni()) {
          // usa valori di default se mancanti
          pstmt.setInt(1, coltivazione.getQuantitaPiante());
          pstmt.setString(2, coltivazione.getNoteTecniche());
          pstmt.setLong(3, progetto.getId());
          pstmt.setLong(4, coltivazione.getColtura().getId());
          pstmt.addBatch();
        }
        pstmt.executeBatch();
      } catch (Exception e) {
        System.err.println("Errore durante l'inserimento delle coltivazioni: " + e.getMessage());
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

}
