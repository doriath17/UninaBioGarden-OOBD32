package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerProgettoColtivatori {

  @FXML
  private TableView<?> coltivatoriProgettoTable;

  @FXML
  private TableColumn<?, ?> usernameColumn;

  @FXML
  private TableColumn<?, ?> nomeColumn;

  @FXML
  private TableColumn<?, ?> emailColumn;

  @FXML
  private TableColumn<?, ?> etaColumn;

  @FXML
  private void initialize() {
    // Setup table columns
  }

  @FXML
  private void editColtivatori(ActionEvent event) {
    // TODO: implement edit logic
  }

}
