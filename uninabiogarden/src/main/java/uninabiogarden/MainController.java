package uninabiogarden;

import java.util.List;

import uninabiogarden.controller.AttivitaController;
import uninabiogarden.dao.DatabaseController;
import uninabiogarden.dto.LottoDto;
import uninabiogarden.dto.OrtoDto;
import uninabiogarden.dto.UtenteDto;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Coltura;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Orto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ValidationException;
import uninabiogarden.entities.Notifica;
import java.util.ArrayList;

public class MainController {
  // ==============================================================================================
  // Sezione: Singleton e costruttore
  // ==============================================================================================

  private static MainController instance;
  private AttivitaController attivitaController = new AttivitaController(this);

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

  // Liste di riferimento caricate dal database
  private List<Orto> orti;
  private List<Coltura> colture;
  private List<Coltivatore> coltivatori;
  private List<Notifica> notifiche;

  // ==============================================================================================
  // Sezione: Accessors
  // ==============================================================================================

  public List<Orto> getOrti() {
    return orti;
  }

  public List<Lotto> getLotti() {
    if (utenteLoggato instanceof Proprietario) {
      return ((Proprietario) utenteLoggato).getLotti();
    }
    return null;
  }

  public List<Progetto> getProgetti() {
    if (utenteLoggato instanceof Proprietario) {
      return ((Proprietario) utenteLoggato).getProgetti();
    } else if (utenteLoggato instanceof Coltivatore) {
      return ((Coltivatore) utenteLoggato).getProgetti();
    }
    return null;
  }

  public List<Coltura> getColture() {
    return colture;
  }

  public Utente getUtenteLoggato() {
    return utenteLoggato;
  }

  public List<Coltivatore> getColtivatori() {
    if (coltivatori == null) {
      caricaColtivatori();
    }
    return coltivatori;
  }

  public List<Notifica> getNotifiche() {
    return notifiche;
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

    // Inizializza le liste osservabili per i nuovi proprietari
    if (utente instanceof Proprietario) {
      caricamentoDatiUtente(utente);
    }
  }

  private void caricamentoDatiUtente(Utente utente) {
    caricaColture(); // deve essere caricato prima dei progetti (richiesto da caricaProgetti)
    caricaColtivatori(); // deve essere caricato prima dei progetti (richiesto da caricaProgetti)

    if (utente instanceof Proprietario) {
      var proprietario = (Proprietario) utente;

      caricaOrti(); // deve essere caricato prima dei lotti
      caricaLotti(proprietario);
      caricaProgetti(proprietario);
      caricaNotifiche();
    } else if (utente instanceof Coltivatore) {
      var coltivatore = (Coltivatore) utente;
      // caricaNotifiche(coltivatore);
      caricaProgetti(coltivatore);
    } else {
      throw new IllegalArgumentException("Tipo utente non riconosciuto");
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
    orti.add(orto);
  }

  private void caricaOrti() {
    orti = databaseController.getOrtoDao().findAll();
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
    var selectedOrto = orti
        .stream()
        .filter(orto -> orto.getId().equals(lottoDto.ortoId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Orto non trovato"));
    lotto.setOrto(selectedOrto);

    Long id = databaseController.getLottoDao().saveLotto(lotto);
    System.out.println("Lotto creato con ID: " + id);
    lotto.setId(id);
    ((Proprietario) utenteLoggato).getLotti().add(lotto);
  }

  private void caricaLotti(Proprietario proprietario) {
    // carica i lotti dal database
    List<Lotto> lotti = databaseController.getLottoDao().findAll(proprietario.getId());

    // associa ogni lotto al proprio orto già caricato in memoria
    // così esiste un unico riferimento per ogni orto
    for (var lotto : lotti) {
      var orto = orti
          .stream()
          .filter(o -> o.getId().equals(lotto.getOrto().getId()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Orto non trovato per lotto: " + lotto.getId()));
      lotto.setOrto(orto);
    }

    proprietario.setLotti(lotti);
    System.out.println("Caricamento lotti effettuato con successo");
  }

  public List<Lotto> getLottiDisponibili() {
    List<Lotto> lotti = databaseController.getLottoDao().findLottiDisponibili(utenteLoggato.getId());
    System.out.println("Lotti disponibili: " + lotti.size());

    for (var lotto : lotti) {
      var orto = orti
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

  public void creaProgetto(Progetto nuovoProgetto) {
    System.out.println("Main Controller: creaProgetto");
    // validazione del progetto
    var validationError = nuovoProgetto.validate();
    if (validationError != null) {
      throw new IllegalArgumentException(validationError);
    }

    Proprietario proprietario = (Proprietario) utenteLoggato;
    nuovoProgetto.setProprietario(proprietario);

    // salva nel database e ottieni l'ID generato
    nuovoProgetto = databaseController.getProgettoDao().saveProgetto(nuovoProgetto);
    risolviProxiesProgetto(nuovoProgetto, proprietario);

    // aggiungi alla lista del proprietario prima del println per evitare
    // che un eventuale NPE in toString() lasci lo stato inconsistente
    proprietario.addProgetto(nuovoProgetto);

    System.out.println("Progetto creato con successo: " + nuovoProgetto.getId());
  }

  public void caricaColtivatori() {
    List<Utente> coltivatori = databaseController.getUtenteDao().findAll("COLTIVATORE");
    this.coltivatori = coltivatori.stream().map(u -> (Coltivatore) u).toList();

    System.out.println("Coltivatori caricati con successo: " + coltivatori.size() + " coltivatori trovati");
  }

  // ==============================================================================================
  // Sezione: Colture
  // ==============================================================================================

  public void caricaColture() {
    if (colture == null || colture.isEmpty()) {
      colture = databaseController.getColturaDao().findAll();
      System.out.println("Caricamento colture effettuato con successo");
    }
  }

  // ==============================================================================================
  // Sezione: Progetti
  // ==============================================================================================

  private void caricaAttivita(Coltivazione coltivazione, Progetto progetto) {
    var attivita = databaseController.getAttivitaDao().findByColtivazione(coltivazione);

    // risolvi il proxy dei coltivatori nelle attività
    attivita.forEach(attivitaItem -> {
      var coltivatore = progetto.getColtivatori().stream()
          .filter(c -> c.getId().equals(attivitaItem.getColtivatore().getId()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Coltivatore non trovato per attivita: " + attivitaItem.getId()));
      attivitaItem.setColtivatore(coltivatore);
    });

    coltivazione.setAttivita(attivita);
    System.out.println("Caricamento attività effettuato con successo: " + attivita.size()
        + " attività trovate per coltivazione: " + coltivazione.getId());
  }

  private void caricaColtivazioni(Progetto progetto) {
    var coltivazioni = databaseController.getColtivazioneDao().findByProgettoId(progetto.getId());

    coltivazioni.forEach(coltivazione -> {
      // risolvi il proxy della coltura
      var resolvedColtura = this.colture.stream()
          .filter(coltura -> coltura.getId().equals(coltivazione.getColtura().getId())).findFirst()
          .orElseThrow(() -> new RuntimeException("Coltura non trovata"));
      coltivazione.setColtura(resolvedColtura);

      // trova le attivita della coltivazione
      caricaAttivita(coltivazione, progetto);
    });

    progetto.setColtivazioni(coltivazioni);
    System.out
        .println("Caricamento coltivazioni effettuato con successo: " + coltivazioni.size()
            + " coltivazioni trovate per progetto: " + progetto.getId());
  }

  private void caricaColtivatori(Progetto progetto) {
    List<Coltivatore> coltivatori = databaseController.getProgettoDao().findColtivatoriIds(progetto.getId());

    // risolvi i proxy dei coltivatori
    coltivatori = coltivatori.stream()
        .map(coltivatore -> this.getColtivatori().stream()
            .filter(c -> c.getId().equals(coltivatore.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Coltivatore non trovato per progetto: " + progetto.getId())))
        .toList();

    progetto.setColtivatori(coltivatori);
    System.out.println("Caricamento coltivatori per progetto effettuato con successo: " + coltivatori.size()
        + " coltivatori trovati per progetto: " + progetto.getId());
  }

  /**
   * Il progetto passato come argomento deve avere:
   * - proxy del lotto (lotto con solo id)
   * - proxy delle coltivazioni (coltivazioni con solo id e coltura con solo id)
   * - proxy dei coltivatori (coltivatori con solo id)
   * 
   * Questo metodo risolve tutti i proxy con gli oggetti gia caricati in memoria o
   * li carica se necessario.
   */
  private void risolviProxiesProgetto(Progetto progetto, Proprietario proprietario) {
    progetto.setProprietario(proprietario);

    // risolvi il proxy del lotto
    var resolvedLotto = proprietario.getLotti().stream()
        .filter(lotto -> lotto.getId().equals(progetto.getLotto().getId())).findFirst()
        .orElseThrow(() -> new RuntimeException("Lotto non trovato"));
    progetto.setLotto(resolvedLotto);

    // risolvi i proxy dei coltivatori e delle coltivazioni
    caricaColtivatori(progetto);
    caricaColtivazioni(progetto);
  }

  // il proprietario deve avere i lotti caricati
  // le colture devono essere gia caricate
  // i coltivatori vengono caricati su necessita (vedi getColtivatori())
  private void caricaProgetti(Proprietario proprietario) {
    List<Progetto> progetti = databaseController.getProgettoDao().findAll(proprietario.getId());

    progetti.forEach(progetto -> {
      risolviProxiesProgetto(progetto, proprietario);
    });

    proprietario.setProgetti(progetti);
    System.out.println("Caricamento progetti effettuato con successo: " +
        progetti.size() + " progetti trovati");
  }

  private void caricaProgetti(Coltivatore coltivatore) {
    List<Progetto> progetti = databaseController.getProgettoDao().findAllByColtivatoreId(coltivatore.getId());

    progetti.forEach(progetto -> {
      caricaColtivatori(progetto);
      caricaColtivazioni(progetto);
    });

    coltivatore.setProgetti(progetti);
    System.out.println("Caricamento progetti effettuato con successo: " +
        progetti.size() + " progetti trovati");
  }

  public Progetto updateProgettoInfo(String nome, String descrizione, Progetto progetto) {
    // validazione del progetto
    if (nome == null || nome.isEmpty()) {
      throw new IllegalArgumentException("Nome progetto mancante");
    }
    if (progetto.getLotto() == null) {
      throw new IllegalArgumentException("Lotto per il progetto non selezionato");
    }

    // aggiorna nel database
    databaseController.getProgettoDao().updateProgetto(nome, descrizione, progetto.getId());

    progetto.setNomeProgetto(nome);
    progetto.setDescrizione(descrizione);

    System.out.println("Progetto aggiornato con successo: " + progetto.getId());
    return progetto;
  }

  public void updateProgetto(String nuovoStato, Progetto progetto) {
    System.out.println("Update stato progetto: nuovo stato: " + nuovoStato + " progetto: " + progetto.getId()
        + " stato attuale: " + progetto.getStato());
    if (progetto.getStato() == Progetto.Stato.ATTIVO && nuovoStato.equals(Progetto.Stato.CONCLUSO.name())) {
      System.out.println("valutazione coltivazioni concluse per progetto: " + progetto.getId());
      List<Coltivazione> coltivazioniConcluse = progetto.getColtivazioni()
          .stream()
          .filter(c -> c.getStato() == Coltivazione.Stato.CONCLUSA)
          .toList();
      System.out.println(
          "Coltivazioni concluse: " + coltivazioniConcluse.size() + " su " + progetto.getColtivazioni().size());
      if (coltivazioniConcluse.size() < progetto.getColtivazioni().size()) {
        System.out.println("Errore intercettato");
        throw new ValidationException("Non è possibile completare un progetto con coltivazioni non concluse");
      }
    }
    databaseController.getProgettoDao().updateProgetto(nuovoStato, progetto.getId());
    progetto.setStato(Progetto.Stato.valueOf(nuovoStato));
    System.out
        .println("Stato del progetto aggiornato con successo: " + progetto.getId() + " nuovo stato: " + nuovoStato);
  }

  // ==============================================================================================
  // Sezione: Delete del Progetto
  // ==============================================================================================

  public void deleteProgetto(Progetto progetto) {
    databaseController.getProgettoDao().deleteProgetto(progetto.getId());
    ((Proprietario) utenteLoggato).removeProgetto(progetto);
    System.out.println("Progetto eliminato con successo: " + progetto.getId());
  }

  // ==============================================================================================
  // Sezione: Coltivazione
  // ==============================================================================================

  public void updateColtivazione(Coltivazione.StatoSalute nuovoStatoSalute, String nuoveNoteTecniche,
      Coltivazione coltivazione) {
    if (nuovoStatoSalute == null) {
      throw new IllegalArgumentException("Nuovo Stato di salute non selezionato");
    }
    if (nuoveNoteTecniche == null) {
      nuoveNoteTecniche = "";
    }

    DatabaseController.getInstance().getColtivazioneDao().update(nuovoStatoSalute.name(), nuoveNoteTecniche,
        coltivazione.getId());
    coltivazione.setStatoSalute(nuovoStatoSalute);
    coltivazione.setNoteTecniche(nuoveNoteTecniche);
    System.out.println("Coltivazione con Id: " + coltivazione.getId() + " aggiornata con successo");
  }

  // ==============================================================================================
  // Sezione: Attivita
  // ==============================================================================================

  public void createAttivita(Attivita attivita, Coltivazione coltivazione) {
    attivitaController.create(attivita, coltivazione);
  }

  public void updateAttivita(Attivita dto, Attivita original, Coltivazione coltivazione) {
    attivitaController.update(dto, original, coltivazione);
  }

  // ==============================================================================================
  // Sezione: Profilo
  // ==============================================================================================

  public void logout() {
    utenteLoggato = null;
    orti = null;
    notifiche = null;
  }

  // ==============================================================================================
  // Sezione: Notifiche
  // ==============================================================================================

  public void caricaNotifiche() {
    notifiche = databaseController.getNotificaDao().getAllNotificheOfUtente(utenteLoggato);
    System.out.println("Notifiche caricate con successo: " + notifiche.size() + " notifiche trovate");
  }

}
