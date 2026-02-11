package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ControllerCreaProgettoStep2 {

  @FXML
  private VBox mainContent;

  @FXML
  private TableView<?> availableColtivatoriTable;

  @FXML
  private TableColumn<?, ?> availableNomeColumn;

  @FXML
  private TableColumn<?, ?> availableUsernameColumn;

  @FXML
  private TableColumn<?, ?> availableSelectionColumn;

  @FXML
  private TableView<?> selectedColtivatoriTable;

  @FXML
  private TableColumn<?, ?> selectedNomeColumn;

  @FXML
  private TableColumn<?, ?> selectedUsernameColumn;

  @FXML
  private TableColumn<?, ?> selectedSelectionColumn;

  @FXML
  private Label errorLabel;

  @FXML
  private void initialize() {
    // initialization if needed
  }

  public void init(ProgettoDto progettoDto) {
  }

  @FXML
  private void selezionaDisponibili(ActionEvent event) {
    // TODO: implement selecting available coltivatori into selected table
  }

  @FXML
  private void deselezionaSelezionati(ActionEvent event) {
    // TODO: implement deselecting selected coltivatori back to available
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    // TODO: implement navigation to previous step/view
  }

  @FXML
  private void nextStepAction(ActionEvent event) {
    // TODO: implement navigation to next step/submit
  }

}
