package uninabiogarden.dao;

import uninabiogarden.entities.Orto;

public class OrtoDao {

  private Database database = Database.getInstance();

  public Long saveOrto(Orto orto) {
    var sql = "INSERT INTO ortO (nome_orto, citta, cap, via, civico, id_proprietario) VALUES (?, ?, ?, ?, ?, ?)";

    Long id = null;

    try (var conn = database.getConnection();
        var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, orto.getNomeOrto());
      stmt.setString(2, orto.getCitta());
      stmt.setString(3, orto.getCap());
      stmt.setString(4, orto.getVia());
      stmt.setString(5, orto.getCivico());
      stmt.setLong(6, orto.getProprietario().getId());

      var rows = stmt.executeUpdate();
      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        id = result.getLong("id");
      } else {
        System.err.println("Creazione orto fallita");
        throw new RuntimeException("Creazione orto fallita");
      }

    } catch (Exception e) {
      System.err.println("Errore durante la creazione dell'orto: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return id;
  }

}
