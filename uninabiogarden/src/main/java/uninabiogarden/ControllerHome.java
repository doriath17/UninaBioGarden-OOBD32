package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    // TODO: implement profile action
  }

  @FXML
  private void progettiAction(ActionEvent event) {
    // TODO: implement projects navigation
  }

  @FXML
  private void attivitaAction(ActionEvent event) {
    // TODO: implement activities navigation
  }

  @FXML
  private void notificheAction(ActionEvent event) {
    // TODO: implement notifications navigation
  }

  @FXML
  private void lottiAction(ActionEvent event) {
    // TODO: implement lots navigation
  }

  @FXML
  private void ReportAction(ActionEvent event) {
    // TODO: implement report navigation
  }

}
