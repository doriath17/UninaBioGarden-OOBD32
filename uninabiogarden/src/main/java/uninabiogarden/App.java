package uninabiogarden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;
  UIController uiController;

  @Override
  public void init() throws Exception {
    super.init();
    System.out.println("App init");
  }

  @Override
  public void start(Stage stage) throws IOException {
    System.out.println("App start");

    // UIController è l'unico controller che viene creato direttamente da App, gli
    // altri controller vengono creati da FXMLLoader quando caricano le rispettive
    // view (vedi loadViewIntoContent in UIController)
    // Si è fatto in questo modo perché UIController è un controller di una view
    // FXML (MainView) e quindi deve essere creato da FXMLLoader, ma allo stesso
    // tempo è anche il controller principale che gestisce la navigazione tra le
    // view, quindi deve essere accessibile da App.
    FXMLLoader loader = new FXMLLoader(App.class.getResource(FxmlView.MAIN_VIEW.getFxmlPath()));
    Pane root = loader.load();
    this.uiController = loader.getController();
    System.out.println("UIController instance in App: " + uiController);
    uiController.init(stage, root);
  }

  public static void main(String[] args) {
    launch();
  }

}