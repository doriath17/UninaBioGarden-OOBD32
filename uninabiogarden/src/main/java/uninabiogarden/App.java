package uninabiogarden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    MainController maincontroller;

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("App init");

        maincontroller = MainController.getInstance();
        // maincontroller.creaSubControllers();

    }

    @Override
    public void start(Stage stage) throws IOException {

        // to load the main controller to sub controllers
        //TOO: turn this into a method

        FXMLLoader loader = loadFXML("LoginView");
        scene = new Scene(loader.getRoot(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).getRoot());
    }

    // private static Parent loadFXML(String fxml) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/uninabiogarden/" + fxml + ".fxml"));
    //     return fxmlLoader.load();
    // }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/uninabiogarden/" + fxml + ".fxml"));
        fxmlLoader.load();
        return fxmlLoader;
    }
  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("primary"), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }

}