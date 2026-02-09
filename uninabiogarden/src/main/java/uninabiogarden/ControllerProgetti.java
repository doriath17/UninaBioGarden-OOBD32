package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;

public class ControllerProgetti {

  @FXML
  private VBox mainContent;

  @FXML
  private ChoiceBox<String> filtroBox;

  @FXML
  private TableView<?> attivitaTable;

  @FXML
  private void initialize() {
    // initialization if needed
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openHomeView();
  }

  @FXML
  private void creaProgettoAction(ActionEvent event) {
    // TODO: implement create project action
  }

}
