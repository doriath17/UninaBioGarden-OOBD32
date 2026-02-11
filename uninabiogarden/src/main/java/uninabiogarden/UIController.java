package uninabiogarden;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.EnumMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
  }

  public void openOrtiView() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.ORTI_VIEW, homeController.getSelectedContent());
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

  public void openCreaProgettoStep1View() {
    ControllerHome homeController = (ControllerHome) controllers.get(FxmlView.HOME_VIEW);
    loadViewIntoContent(FxmlView.CREA_PROGETTO_STEP_1, homeController.getSelectedContent());
  }

}
