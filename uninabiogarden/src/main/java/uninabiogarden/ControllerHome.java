package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Coltivatore;

public class ControllerHome {

  @FXML
  private BorderPane mainContent;

  @FXML
  private VBox selectedContent;

  Pane getSelectedContent() {
    return selectedContent;
  }

  @FXML
  private void profiloAction(ActionEvent event) {
    UIController.getInstance().openProfiloView();
  }

  @FXML
  private void dashboardAction(ActionEvent event) {
    if (MainController.getInstance().getUtenteLoggato() instanceof Coltivatore) {
      UIController.getInstance().openColtivatoreHomeView();
    } else {
      UIController.getInstance().openProprietarioHomeView();
    }
  }

  @FXML
  private void progettiAction(ActionEvent event) {
    UIController.getInstance().openProgettiView();
  }

  @FXML
  private void attivitaAction(ActionEvent event) {
    // TODO: implement activities navigation
  }

  @FXML
  private void notificheAction(ActionEvent event) {
    UIController.getInstance().openNotificheView();
  }

  @FXML
  private void lottiAction(ActionEvent event) {
    UIController.getInstance().openLottiView();
  }

  @FXML
  private void ortiAction(ActionEvent event) {
    UIController.getInstance().openOrtiView();
  }

  @FXML
  private void ReportAction(ActionEvent event) {
    // TODO: implement report navigation
  }

}
