package uninabiogarden;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.EnumMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.Progetto;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class UIController {

  // Nota che questa istanza è creata da FXMLLoader quando carica la MainView in
  // App.start.
  // Quindi non è possibile creare un'istanze direttamente qui con "new
  // UIController()", altrimenti si avrebbero due istanze diverse di UIController,
  // una creata da FXMLLoader e una creata manualmente, e non funzionerebbe la
  // comunicazione tra i controller.
  // L'istanza viene inizializzata in App.start quando si chiama init() sul
  // controller.
  // In questo modo i subController possono accedere all'istanza principale di
  // UIController tramite il metodo getInstance() e comunicare con essa.
  static UIController instance;

  public static UIController getInstance() {
    return instance;
  }

  Stage primaryStage;
  Scene scene;

  @FXML
  VBox mainPane;

  EnumMap<FxmlView, Pane> loadedViews = new EnumMap<>(FxmlView.class);
  EnumMap<FxmlView, Object> controllers = new EnumMap<>(FxmlView.class);

  public void init(Stage primaryStage, Pane root) {
    this.primaryStage = primaryStage;
    UIController.instance = this;

    // crea la scena iniziale (main view + login view)
    loadedViews.put(FxmlView.MAIN_VIEW, root);
    openLoginView();

    this.scene = new Scene(root, 1280, 720);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  Pane getView(FxmlView view) {
    if (!loadedViews.containsKey(view)) {
      try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(view.getFxmlPath()));
        loadedViews.put(view, loader.load());
        controllers.put(view, loader.getController());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return loadedViews.get(view); // null se view non è stato caricato
  }

  void loadViewIntoContent(FxmlView view, Pane contentPane) {
    Pane viewRootPane = getView(view);
    if (viewRootPane != null) {
      contentPane.getChildren().setAll(viewRootPane);
    } else {
      System.err.println("View not found: " + view);
    }
  }

  public void openLoginView() {
    loadViewIntoContent(FxmlView.LOGIN_VIEW, mainPane);
  }

  public void openSignUpView() {
    loadViewIntoContent(FxmlView.SIGNUP_VIEW, mainPane);
  }

  public void openHomeView() {
    loadViewIntoContent(FxmlView.HOME_VIEW, mainPane);
  }

  public void openColtivatoreHomeView() {
    loadViewIntoContent(FxmlView.HOME_VIEW, mainPane);
  }

  public void openProprietarioHomeView() {
    openHomeView();
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.DASHBOARD_VIEW, homeController.getSelectedContent());
  }

  public void openProgettiView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.PROGETTI_VIEW, homeController.getSelectedContent());

    ControllerProgetti progettiController = (ControllerProgetti) controllers.get(FxmlView.PROGETTI_VIEW);
    progettiController.init(); // per avere la lista aggiornata dei progetti ogni volta che si apre la view
  }

  public void openOrtiView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.ORTI_VIEW, homeController.getSelectedContent());

    ControllerOrti ortiController = (ControllerOrti) controllers.get(FxmlView.ORTI_VIEW);
    ortiController.init(); // per avere la lista aggiornata degli orti ogni volta che si apre la view
  }

  public void openCreaOrtoView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_ORTO_VIEW, homeController.getSelectedContent());
  }

  public void openLottiView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.LOTTI_VIEW, homeController.getSelectedContent());

    ControllerLotti lottiController = (ControllerLotti) controllers.get(FxmlView.LOTTI_VIEW);
    lottiController.init(); // per avere la lista aggiornata dei lotti ogni volta che si apre la view
  }

  public void openCreaLottoView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_LOTTO_VIEW, homeController.getSelectedContent());
  }

  public void openProfiloView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.PROFILO_VIEW, homeController.getSelectedContent());
  }

  public void openCreaProgettoStep1View(Progetto nuovoProgetto, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_PROGETTO_STEP_1, homeController.getSelectedContent());

    if (init) {
      ControllerCreaProgettoStep1 step1Controller = (ControllerCreaProgettoStep1) controllers
          .get(FxmlView.CREA_PROGETTO_STEP_1);
      step1Controller.init(nuovoProgetto);
    }
  }

  public void openCreaProgettoStep2View(Progetto nuovoProgetto, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_PROGETTO_STEP_2, homeController.getSelectedContent());

    if (init) {
      ControllerCreaProgettoStep2 step2Controller = (ControllerCreaProgettoStep2) controllers
          .get(FxmlView.CREA_PROGETTO_STEP_2);
      step2Controller.init(nuovoProgetto);
    }
  }

  public void openCreaProgettoStep3View(Progetto nuovoProgetto, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_PROGETTO_STEP_3, homeController.getSelectedContent());

    if (init) {
      ControllerCreaProgettoStep3 step3Controller = (ControllerCreaProgettoStep3) controllers
          .get(FxmlView.CREA_PROGETTO_STEP_3);
      step3Controller.init(nuovoProgetto);
    }
  }

  public void openDettaglioProgettoView(Progetto progetto) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.DETTAGLIO_PROGETTO_VIEW, homeController.getSelectedContent());

    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    dettaglioController.init(progetto);

    // Open info generali di default
    openProgettoInfoGenerali(progetto, dettaglioController.getErrorLabel());
  }

  public void openProgettoInfoGenerali(Progetto progetto, javafx.scene.control.Label errorLabel) {
    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    loadViewIntoContent(FxmlView.PROGETTO_INFO_GENERALI, dettaglioController.getDettaglioContent());

    ControllerProgettoInfoGenerali infoGeneraliController = (ControllerProgettoInfoGenerali) controllers
        .get(FxmlView.PROGETTO_INFO_GENERALI);
    infoGeneraliController.init(progetto, errorLabel);
  }

  public void openProgettoColtivatori(Progetto progetto, javafx.scene.control.Label errorLabel) {
    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    loadViewIntoContent(FxmlView.PROGETTO_COLTIVATORI, dettaglioController.getDettaglioContent());

    ControllerProgettoColtivatori coltivatoriController = (ControllerProgettoColtivatori) controllers
        .get(FxmlView.PROGETTO_COLTIVATORI);
    coltivatoriController.init(progetto, errorLabel);
  }

  public void openProgettoColtivazioni(Progetto progetto, javafx.scene.control.Label errorLabel) {
    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    loadViewIntoContent(FxmlView.PROGETTO_COLTIVAZIONI, dettaglioController.getDettaglioContent());

    ControllerProgettoColtivazioni coltivazioniController = (ControllerProgettoColtivazioni) controllers
        .get(FxmlView.PROGETTO_COLTIVAZIONI);
    coltivazioniController.init(progetto, errorLabel);
  }

  public void openProgettoAttivita(Progetto progetto, javafx.scene.control.Label errorLabel) {
    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    loadViewIntoContent(FxmlView.PROGETTO_ATTIVITA, dettaglioController.getDettaglioContent());

    ControllerProgettoAttivita attivitaController = (ControllerProgettoAttivita) controllers
        .get(FxmlView.PROGETTO_ATTIVITA);
    attivitaController.init(progetto, errorLabel);
  }

  public void openProgettoNotifiche(Progetto progetto, javafx.scene.control.Label errorLabel) {
    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    loadViewIntoContent(FxmlView.PROGETTO_NOTIFICHE, dettaglioController.getDettaglioContent());

    ControllerProgettoNotifiche notificheController = (ControllerProgettoNotifiche) controllers
        .get(FxmlView.PROGETTO_NOTIFICHE);
    notificheController.init(progetto, errorLabel);
  }

  // --
  // =======================================================================================================
  // -- Notifiche
  // --
  // =======================================================================================================

  public void openNotificheView(boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.NOTIFICHE_VIEW, homeController.getSelectedContent());

    if (init) {
      ControllerNotifiche notificheController = (ControllerNotifiche) controllers.get(FxmlView.NOTIFICHE_VIEW);
      notificheController.init(); // per avere la lista aggiornata delle notifiche ogni volta che si apre la view
    }
  }

  public void openCreaNotificheView(Notifica nuovaNotifica, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_NOTIFICHE_VIEW, homeController.getSelectedContent());

    if (init) {
      ControllerCreaNotifica creaNotificaController = (ControllerCreaNotifica) controllers
          .get(FxmlView.CREA_NOTIFICHE_VIEW);
      creaNotificaController.init(nuovaNotifica);
    }
  }

  public void openCreaNotificheAttivitaView(Notifica nuovaNotifica, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_NOTIFICHE_ATTIVITA_VIEW, homeController.getSelectedContent());

    if (init) {
      ControllerCreaNotificaAttivita creaNotificaAttivitaController = (ControllerCreaNotificaAttivita) controllers
          .get(FxmlView.CREA_NOTIFICHE_ATTIVITA_VIEW);
      creaNotificaAttivitaController.init(nuovaNotifica);
    }
  }

  public void openCreaNotificaStep2View(Notifica nuovaNotifica, boolean init) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_NOTIFICHE_STEP_2, homeController.getSelectedContent());

    if (init) {
      ControllerCreaNotificaStep2 step2Controller = (ControllerCreaNotificaStep2) controllers
          .get(FxmlView.CREA_NOTIFICHE_STEP_2);
      step2Controller.init(nuovaNotifica);
    }
  }

  public void backToProgettoColtivazioni(Progetto progetto) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.DETTAGLIO_PROGETTO_VIEW, homeController.getSelectedContent());

    ControllerDettaglioProgetto dettaglioController = (ControllerDettaglioProgetto) controllers
        .get(FxmlView.DETTAGLIO_PROGETTO_VIEW);
    dettaglioController.init(progetto);
    openProgettoColtivazioni(progetto, dettaglioController.getErrorLabel());
  }

  public void openDettaglioColtivazioneView(Progetto progetto, Coltivazione coltivazione) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.DETTAGLIO_COLTIVAZIONE, homeController.getSelectedContent());

    ControllerDettaglioColtivazione dettaglioColtivazioneController = (ControllerDettaglioColtivazione) controllers
        .get(FxmlView.DETTAGLIO_COLTIVAZIONE);
    dettaglioColtivazioneController.init(progetto, coltivazione);
  }

  public void openDettaglioAttivitaView(Progetto progetto, Coltivazione coltivazione) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.DETTAGLIO_ATTIVITA, homeController.getSelectedContent());

    ControllerDettaglioAttivita dettaglioAttivitaController = (ControllerDettaglioAttivita) controllers
        .get(FxmlView.DETTAGLIO_ATTIVITA);
    dettaglioAttivitaController.init(progetto, coltivazione);
  }

  public void openPianificazioneAttivita(Progetto progetto, Coltivazione coltivazione) {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.PIANIFICAZIONE_ATTIVITA, homeController.getSelectedContent());

    ControllerCreaAttivita creaAttivitaController = (ControllerCreaAttivita) controllers
        .get(FxmlView.PIANIFICAZIONE_ATTIVITA);
    creaAttivitaController.init(progetto, coltivazione);
  }

}
