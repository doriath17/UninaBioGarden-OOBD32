package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Coltura;

public class ColtivazioneDao {

  private Database database = Database.getInstance();

  public List<Coltivazione> findByProgettoId(Long id) {
    var sql = """
        SELECT *
        FROM coltivazione
        WHERE id_progetto = ?
        """;

    try (Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();

      List<Coltivazione> coltivazioni = new ArrayList<>();
      while (rs.next()) {
        Coltivazione coltivazione = new Coltivazione();
        coltivazione.setId(rs.getLong("id"));
        coltivazione.setDataInizio(rs.getDate("data_inizio").toLocalDate());
        coltivazione.setNoteTecniche(rs.getString("note_tecniche"));
        coltivazione.setStatoSalute(Coltivazione.StatoSalute.valueOf(rs.getString("stato_salute")));
        coltivazione.setStato(Coltivazione.Stato.valueOf(rs.getString("stato")));

        // proxy della coltura (gia caricata nel progetto)
        var coltura = new Coltura();
        coltura.setId(rs.getLong("id_coltura"));
        coltivazione.setColtura(coltura);

        coltivazioni.add(coltivazione);
      }
      return coltivazioni;
    } catch (Exception e) {
      System.err.println("Errore durante il recupero delle coltivazioni: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero delle coltivazioni. Riprova più tardi.");
    }
  }

  public void update(String nuovoStato, String nuoveNoteTecniche, Long idColtivazione) {
    var sql = """
        UPDATE coltivazione
        SET stato_salute = ?::stato_salute_coltivazione, note_tecniche = ?
        WHERE id = ?
        """;
    try (Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, nuovoStato);
      stmt.setString(2, nuoveNoteTecniche);
      stmt.setLong(3, idColtivazione);
      stmt.executeUpdate();
    } catch (Exception e) {
      System.err.println("Errore durante l'aggiornamento della coltivazione: " + e.getMessage());
      throw new RuntimeException("Errore durante l'aggiornamento della coltivazione. Riprova più tardi.");
    }
  }
}