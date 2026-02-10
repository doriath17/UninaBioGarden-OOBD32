package uninabiogarden;

import uninabiogarden.dao.DatabaseController;
import uninabiogarden.dto.UtenteDto;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class MainController {

  private static MainController instance;

  public static MainController getInstance() {
    if (instance == null) {
      instance = new MainController();
    }
    return instance;
  }

  private MainController() {
    // private constructor to prevent instantiation
  }

  private DatabaseController databaseController = DatabaseController.getInstance();
  private Utente utenteLoggato;

  public Utente getUtenteLoggato() {
    return utenteLoggato;
  }

  private String isValidUtenteDto(UtenteDto utenteDto) {
    if (utenteDto.username == null || utenteDto.username.isEmpty()) {
      return "Username mancante";
    }

    if (utenteDto.password == null || utenteDto.password.isEmpty()) {
      return "Password mancante";
    }

    if (utenteDto.email == null || utenteDto.email.isEmpty()) {
      return "Email mancante";
    }

    if (!utenteDto.email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      return "Email non valida";
    }

    if (utenteDto.codiceFiscale == null || utenteDto.codiceFiscale.isEmpty()) {
      return "Codice fiscale mancante";
    }

    if (!utenteDto.codiceFiscale.matches("[A-Za-z0-9]+")) {
      return "Codice fiscale non valido";
    }

    if (utenteDto.nome == null || utenteDto.nome.isEmpty()) {
      return "Nome mancante";
    }

    if (utenteDto.cognome == null || utenteDto.cognome.isEmpty()) {
      return "Cognome mancante";
    }

    if (utenteDto.bDay == null) {
      return "Data di nascita mancante";
    }

    if (utenteDto.bDay == null || utenteDto.bDay.isAfter(java.time.LocalDate.now().minusYears(18))) {
      return "L'utente deve essere maggiorenne";
    }

    return null; // dati validi
  }

  public void registraUtente(UtenteDto utenteDto) {
    // validazione dei dati
    String validationError = isValidUtenteDto(utenteDto);
    if (validationError != null) {
      throw new IllegalArgumentException(validationError);
    }

    // creazione dell'utente
    Utente utente = null;
    if (utenteDto.isColtivatore) {
      utente = new Coltivatore(utenteDto);
    } else {
      utente = new Proprietario(utenteDto);
    }

    Long id = databaseController.getUtenteDao().saveUtente(utente);
    System.out.println("Utente registrato con ID: " + id);
    utente.setId(id);
    utenteLoggato = utente;
  }

  public void loginUtente(String username, String password) {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username mancante");
    }
    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password mancante");
    }

    utenteLoggato = databaseController.getUtenteDao().getUtenteByUsername(username);
    if (utenteLoggato == null) {
      throw new IllegalArgumentException("Utente non trovato");
    }
    System.out.println("Login effettuato: " + utenteLoggato.getUsername());
  }

}
