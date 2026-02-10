package uninabiogarden.dao;

import uninabiogarden.dto.UtenteDto;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class UtenteDao {

  private Database database = Database.getInstance();

  public Long saveUtente(Utente utente) {
    String isColtivatore = utente instanceof Coltivatore ? "coltivatore" : "proprietario";

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

  public Utente getUtenteByUsername(String username) {
    var sql = "SELECT * FROM utente WHERE username = '" + username + "'";

    Utente foundUtente = null;

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      if (result.next()) { // utente trovato
        UtenteDto dto = new UtenteDto();
        dto.username = result.getString("username");
        dto.password = result.getString("password");
        dto.email = result.getString("email");
        dto.nome = result.getString("nome");
        dto.cognome = result.getString("cognome");
        dto.bDay = result.getDate("b_day").toLocalDate();
        dto.codiceFiscale = result.getString("codice_fiscale");
        dto.gender = result.getString("gender");
        dto.bio = result.getString("bio");
        dto.isColtivatore = "coltivatore".equals(result.getString("tipo"));

        if (dto.isColtivatore) {
          foundUtente = new Coltivatore(dto);
        } else {
          foundUtente = new Proprietario(dto);
        }
      }
    } catch (Exception e) {
      System.err.println("Errore durante la ricerca utente: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return foundUtente; // null se non trovato
  }

}
