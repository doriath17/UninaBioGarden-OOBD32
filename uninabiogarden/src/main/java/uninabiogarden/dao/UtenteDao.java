package uninabiogarden.dao;

import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class UtenteDao {

  private Database database = Database.getInstance();

  public Long saveUtente(Utente utente) {
    String isColtivatore = utente instanceof Coltivatore ? "COLTIVATORE" : "PROPRIETARIO";

    String sql = "INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, gender, bio, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::tipo_utente)";

    Long id = null;

    try (var conn = database.getConnection();
        var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, utente.getUsername());
      stmt.setString(2, utente.getPassword());
      stmt.setString(3, utente.getEmail());
      stmt.setString(4, utente.getNome());
      stmt.setString(5, utente.getCognome());
      stmt.setDate(6, java.sql.Date.valueOf(utente.getbDay()));
      stmt.setString(7, utente.getCodiceFiscale());
      stmt.setString(8, utente.getGender());
      stmt.setString(9, utente.getBio());
      stmt.setString(10, isColtivatore);
      var rows = stmt.executeUpdate();
      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        id = result.getLong("id");
      } else {
        System.err.println("Registrazione utente fallita");
        return null;
      }

    } catch (Exception e) {
      System.err.println("Errore durante la registrazione utente: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return id;
  }

  private Utente buildUtente(java.sql.ResultSet result) throws java.sql.SQLException {
    String tipo = result.getString("tipo");
    String bDay = result.getDate("b_day") != null ? result.getDate("b_day").toString() : null;
    Utente utente;
    if ("COLTIVATORE".equals(tipo)) {
      utente = new Coltivatore(
          result.getString("username"), result.getString("password"), result.getString("email"),
          result.getString("codice_fiscale"), result.getString("nome"), result.getString("cognome"),
          bDay, result.getString("gender"), result.getString("bio"));
    } else {
      utente = new Proprietario(
          result.getString("username"), result.getString("password"), result.getString("email"),
          result.getString("codice_fiscale"), result.getString("nome"), result.getString("cognome"),
          bDay, result.getString("gender"), result.getString("bio"));
    }
    utente.setId(result.getLong("id"));
    return utente;
  }

  public Utente getUtenteByUsername(String username) {
    var sql = "SELECT * FROM utente WHERE username = '" + username + "'";

    Utente foundUtente = null;

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      if (result.next()) { // utente trovato
        foundUtente = buildUtente(result);
      }
    } catch (Exception e) {
      System.err.println("Errore durante la ricerca utente: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return foundUtente; // null se non trovato
  }

  public List<Utente> findAll(String tipo) {
    var sql = "SELECT * FROM utente WHERE tipo = '" + tipo + "'";

    List<Utente> utenti = new ArrayList<>();

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      while (result.next()) {
        utenti.add(buildUtente(result));
      }
    } catch (Exception e) {
      System.err.println("Errore durante la ricerca utenti: " + e.getMessage());
      throw new RuntimeException("Errore durante la ricerca utenti");
    }

    return utenti;
  }
}