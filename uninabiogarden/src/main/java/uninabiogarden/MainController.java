package uninabiogarden;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uninabiogarden.dao.DatabaseController;
import uninabiogarden.dto.LottoDto;
import uninabiogarden.dto.OrtoDto;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.dto.UtenteDto;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Orto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class MainController {
  // ==============================================================================================
  // Sezione: Singleton e costruttore
  // ==============================================================================================

  private static MainController instance;

  public static MainController getInstance() {
    if (instance == null) {
      instance = new MainController();
    }
    return instance;
  }

  private MainController() {
    // costruttore privato per evitare istanziazioni esterne
  }

  // ==============================================================================================
  // Sezione: Dipendenze e stato dell'applicazione
  // ==============================================================================================

  private DatabaseController databaseController = DatabaseController.getInstance();

  private Utente utenteLoggato;

  private ObservableList<Orto> ortiObservableList = FXCollections.observableArrayList();

  // questa lista viene inizializzata in modo tale che tutte le modifiche su di
  // essa si riflettano direttamente sulla lista di lotti nel proprietario loggato
  // vedi caricamentoDatiUtente() per maggiori dettagli
  private ObservableList<Lotto> lottiObservableList;

  // ==============================================================================================
  // Sezione: Accessors
  // ==============================================================================================

  public ObservableList<Orto> getOrtiObservableList() {
    return ortiObservableList;
  }

  public ObservableList<Lotto> getLottiObservableList() {
    return lottiObservableList;
  }

  public Utente getUtenteLoggato() {
    return utenteLoggato;
  }

  // ==============================================================================================
  // Sezione: Validazione dati Utente
  // ==============================================================================================

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

    if (utenteDto.bDay == null || utenteDto.bDay.isEmpty()) {
      return "Data di nascita mancante";
    }

    // Parse and validate bDay
    try {
      java.time.LocalDate birthDate = java.time.LocalDate.parse(utenteDto.bDay);
      if (birthDate.isAfter(java.time.LocalDate.now().minusYears(18))) {
        return "L'utente deve essere maggiorenne";
      }
    } catch (Exception e) {
      return "Formato data di nascita non valido";
    }

    return null; // dati validi
  }

  // ==============================================================================================
  // Sezione: Registrazione e login
  // ==============================================================================================

  public void registraUtente(UtenteDto utenteDto) {
    // validazione dei dati
    String validationError = isValidUtenteDto(utenteDto);
    if (validationError != null) {
      throw new IllegalArgumentException(validationError);
    }

    // creazione dell'utente
    Utente utente = null;
    if (utenteDto.tipo.equals("COLTIVATORE")) {
      utente = new Coltivatore(utenteDto);
    } else {
      utente = new Proprietario(utenteDto);
    }

    Long id = databaseController.getUtenteDao().saveUtente(utente);
    System.out.println("Utente registrato con ID: " + id);
    utente.setId(id);
    utenteLoggato = utente;
  }

  private void caricamentoDatiUtente(Utente utente) {
    if (utente instanceof Proprietario) {
      var proprietario = (Proprietario) utente;

      caricaOrti();
      caricaLotti(proprietario);

      // carica i progetti

    }
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

    try {
      caricamentoDatiUtente(utenteLoggato);
    } catch (Exception e) {
      System.err.println("Errore durante il login: " + e.getMessage());
      throw new RuntimeException("Errore durante il login: errore durante il caricamento dei dati");
    }

    System.out.println("Login effettuato: " + utenteLoggato.getUsername());
  }

  // ==============================================================================================
  // Sezione: Validazione e creazione Orto
  // ==============================================================================================

  private void isValidOrto(OrtoDto ortoDto) {
    if (ortoDto.nomeOrto == null || ortoDto.nomeOrto.isEmpty()) {
      throw new IllegalArgumentException("Nome orto mancante");
    }
    if (ortoDto.citta == null || ortoDto.citta.isEmpty()) {
      throw new IllegalArgumentException("Città mancante");
    }
    if (ortoDto.cap == null || ortoDto.cap.isEmpty()) {
      throw new IllegalArgumentException("CAP mancante");
    }
    if (!ortoDto.cap.matches("^[0-9]{5}$")) {
      throw new IllegalArgumentException("CAP non valido o mancante");
    }
    if (ortoDto.via == null || ortoDto.via.isEmpty()) {
      throw new IllegalArgumentException("Via mancante");
    }
  }

  public void creaOrto(OrtoDto ortoDto) {
    isValidOrto(ortoDto);

    Orto orto = new Orto(ortoDto);
    orto.setProprietario((Proprietario) utenteLoggato);
    Long id = databaseController.getOrtoDao().saveOrto(orto);
    System.out.println("Orto creato con ID: " + id);
    orto.setId(id);
    ortiObservableList.add(orto);
  }

  private void caricaOrti() {
    List<Orto> orti = databaseController.getOrtoDao().findAll();
    ortiObservableList.setAll(orti);
    System.out.println("Caricamento orti effettuato con successo");
  }

  // ==============================================================================================
  // Sezione: Validazione e creazione Lotto
  // ==============================================================================================

  private String isValidLotto(LottoDto lottoDto) {
    if (lottoDto.codiceLotto == null || lottoDto.codiceLotto.isEmpty()) {
      return "Codice lotto mancante";
    }
    if (lottoDto.estensioneMq == null || lottoDto.estensioneMq <= 0) {
      return "Estensione del lotto mancante o non valida, (deve essere un numero positivo)";
    }
    if (lottoDto.ortoId == null) {
      return "Orto per il lotto non selezionato";
    }
    return null;
  }

  public void creaLotto(LottoDto lottoDto) {
    // validazione dati lotto
    var validationError = isValidLotto(lottoDto);
    if (validationError != null) {
      throw new IllegalArgumentException(validationError);
    }

    // recupera il proprietario e l'orto associati al lotto
    Lotto lotto = new Lotto(lottoDto);
    lotto.setProprietario((Proprietario) utenteLoggato);
    var selectedOrto = ortiObservableList
        .stream()
        .filter(orto -> orto.getId().equals(lottoDto.ortoId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Orto non trovato"));
    lotto.setOrto(selectedOrto);

    Long id = databaseController.getLottoDao().saveLotto(lotto);
    System.out.println("Lotto creato con ID: " + id);
    lotto.setId(id);
    lottiObservableList.add(lotto);
  }

  private void caricaLotti(Proprietario proprietario) {
    // carica i lotti
    List<Lotto> lotti = databaseController.getLottoDao().findAll(proprietario.getId());

    // associa ogni lotto al proprio orto
    // cosi in memoria esiste un unico orto
    for (var lotto : lotti) {
      var orto = ortiObservableList
          .stream()
          .filter(o -> o.getId().equals(lotto.getOrto().getId()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Orto non trovato per lotto: " + lotto.getId()));
      lotto.setOrto(orto);
    }

    proprietario.setLotti(lotti);
    // passaggio fondamentale per rendere la lista di lotti del proprietario
    // osservabile dai componenti di JavaFX
    this.lottiObservableList = FXCollections.observableList(lotti);

    System.out.println("Caricamento lotti effettuato con successo");
  }

  public List<Lotto> getLottiDisponibili() {
    List<Lotto> lotti = databaseController.getLottoDao().findLottiDisponibili(utenteLoggato.getId());
    System.out.println("Lotti disponibili: " + lotti.size());

    for (var lotto : lotti) {
      var orto = ortiObservableList
          .stream()
          .filter(o -> o.getId().equals(lotto.getOrto().getId()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Orto non trovato per lotto: " + lotto.getId()));
      lotto.setOrto(orto);
    }

    return lotti;
  }

  // ==============================================================================================
  // Sezione: Validazione e creazione Progetto
  // ==============================================================================================

  public void creaProgetto(ProgettoDto progettoDto) {
    // // validazione dati progetto
    // var validationError = isValidProgetto(progettoDto);
    // if (validationError != null) {
    // throw new IllegalArgumentException(validationError);
    // }

    // // conversione nell'entità Progetto
    // Progetto progetto = new Progetto(progettoDto);

    // progetto = databaseController.getProgettoDao().saveProgetto(progetto);
    // System.out.println("Progetto creato con ID: " + progetto.getId());

    // // associa il progetto al proprietario loggato
    // ((Proprietario) utenteLoggato).addProgetto(progetto);

  }

  // ==============================================================================================
  // Sezione: Coltivatori e assegnazione a Progetto
  // ==============================================================================================

  public List<Coltivatore> getColtivatoriDisponibili() {
    List<Utente> coltivatori = databaseController.getUtenteDao().findAll("COLTIVATORE");
    System.out.println("Coltivatori disponibili: " + coltivatori.size());
    return coltivatori.stream().map(u -> (Coltivatore) u).toList();
  }

}
