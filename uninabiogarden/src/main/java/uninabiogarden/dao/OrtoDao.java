package uninabiogarden.dao;

import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Orto;
import uninabiogarden.entities.Proprietario;

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

  public List<Orto> findAll() {
    var sql = "SELECT * FROM orto JOIN utente ON orto.id_proprietario = utente.id";

    List<Orto> orti = new ArrayList<>();

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      while (result.next()) { // orto trovato
        Proprietario proprietario = new Proprietario();
        proprietario.setId(result.getLong("id_proprietario"));
        proprietario.setUsername(result.getString("username"));
        proprietario.setEmail(result.getString("email"));
        proprietario.setCodiceFiscale(result.getString("codice_fiscale"));
        proprietario.setNome(result.getString("nome"));
        proprietario.setCognome(result.getString("cognome"));
        proprietario.setbDay(result.getDate("b_day").toLocalDate());
        proprietario.setGender(result.getString("gender"));
        proprietario.setBio(result.getString("bio"));

        Orto orto = new Orto(
            result.getString("nome_orto"),
            result.getString("citta"),
            result.getString("cap"),
            result.getString("civico"),
            result.getString("via"),
            proprietario);
        orto.setId(result.getLong("id"));
        orti.add(orto);
      }

    } catch (Exception e) {
      System.err.println("Errore durante il recupero degli orti: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return orti;
  }

}
