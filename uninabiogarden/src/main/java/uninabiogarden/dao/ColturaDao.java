package uninabiogarden.dao;

import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Coltura;

public class ColturaDao {

  private Database database = Database.getInstance();

  public List<Coltura> findAll() {
    String query = "SELECT id, nome_comune, tempo_maturazione, caratteristiche FROM coltura";

    List<Coltura> colture = new ArrayList<>();

    try (var conn = database.getConnection();
        var stmt = conn.prepareStatement(query);
        var rs = stmt.executeQuery()) {
      while (rs.next()) {
        Coltura coltura = new Coltura();
        coltura.setId(rs.getLong("id"));
        coltura.setNomeComune(rs.getString("nome_comune"));
        coltura.setTempoMaturazione(rs.getInt("tempo_maturazione"));
        coltura.setCaratteristiche(rs.getString("caratteristiche"));
        colture.add(coltura);
      }
    } catch (Exception e) {
      System.err.println("Errore durante il recupero delle colture: " + e.getMessage());
      throw new RuntimeException("Errore durante il recupero delle colture. Riprova più tardi.");
    }
    return colture;
  }
}
