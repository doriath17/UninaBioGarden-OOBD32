package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.chart.PieChart.Data;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltura;
import uninabiogarden.entities.Progetto;

public class ProgettoDao {

  private Database database = Database.getInstance();

  public Progetto saveProgetto(Progetto progetto) {
    return null;
    // Connection conn = null;
    // try {
    // conn = database.getConnection();
    // conn.setAutoCommit(false); // Inizia la transazione (non fa direttamente il
    // commit dopo ogni operazione)

    // // 1. Inserisci il progetto
    // String insertProgettoSql = "INSERT INTO progetto (nome_progetto, descrizione,
    // id_proprietario, id_lotto) VALUES (?, ?, ?, ?)";
    // try (PreparedStatement pstmt = conn.prepareStatement(insertProgettoSql,
    // Statement.RETURN_GENERATED_KEYS)) {
    // pstmt.setString(1, progetto.getNomeProgetto());
    // pstmt.setString(2, progetto.getDescrizione());
    // pstmt.setLong(3, progetto.getProprietario().getId());
    // pstmt.setLong(4, progetto.getLotto().getId());
    // pstmt.executeUpdate();

    // // Ottieni l'ID generato per il progetto
    // // ResultSet è come un cursore che punta ai risultati della query, in questo
    // // caso alle chiavi generate
    // try (ResultSet rs = pstmt.getGeneratedKeys()) {
    // if (rs.next()) {
    // progetto.setId(rs.getLong(1));
    // }
    // }
    // }

    // // 2. Insert multiple coltivazioni in batch
    // String insertColtivazioneSql = "INSERT INTO coltivazione (quantita_piante,
    // note_tecniche, id_progetto, id_coltura) VALUES (?, ?, ?, ?)";
    // try (PreparedStatement pstmt = conn.prepareStatement(insertColtivazioneSql))
    // {
    // for (Coltura coltura : progetto.getColture()) {
    // pstmt.setInt(1, 0); // Default value
    // pstmt.setString(2, "");
    // pstmt.setLong(3, progetto.getId());
    // pstmt.setLong(4, coltura.getId());
    // pstmt.addBatch();
    // }
    // pstmt.executeBatch();
    // }

    // // 3. Insert multiple lavora_per in batch
    // String insertLavoraPerSql = "INSERT INTO lavora_per (id_coltivatore,
    // id_progetto) VALUES (?, ?)";
    // try (PreparedStatement pstmt = conn.prepareStatement(insertLavoraPerSql)) {
    // for (Coltivatore coltivatore : progetto.getColtivatori()) {
    // pstmt.setLong(1, coltivatore.getId());
    // pstmt.setLong(2, progetto.getId());
    // pstmt.addBatch();
    // }
    // pstmt.executeBatch();
    // }

    // conn.commit(); // Fa il commit della transazione se tutto va a buon termine
    // return progetto;

    // } catch (Exception e) {
    // if (conn != null) {
    // try {
    // conn.rollback(); // Esegue il rollback in caso di errore
    // } catch (SQLException ex) {
    // ex.printStackTrace();
    // }
    // }
    // throw new RuntimeException("Error saving progetto", e);
    // } finally {
    // if (conn != null) {
    // try {
    // conn.setAutoCommit(true);
    // conn.close();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
    // }
  }

}
