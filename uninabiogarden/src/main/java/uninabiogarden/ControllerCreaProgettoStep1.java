package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ControllerCreaProgettoStep1 {

  @FXML
  private VBox mainContent;

  @FXML
  private TableView<?> availableOrtiTable;

  @FXML
  private TableColumn<?, ?> nomeOrtoColumn;

  @FXML
  private TableColumn<?, ?> indirizzoOrtoColumn;

  @FXML
  private TextField nomeProgettoField;

  @FXML
  private TextArea descrizioneField;

  @FXML
  private Label codiceSelectedLotto;

  @FXML
  private Label nomeOrtoSelectedLotto;

  @FXML
  private Label indirizzoSelectedLotto;

  @FXML
  private Label errorLabel;

  @FXML
  private void initialize() {
    // initialization if needed
  }

  @FXML
  private void indietroAction() {
    // TODO: implement navigation to previous view
  }

  @FXML
  private void nextStepAction() {
    // TODO: implement next step action
  }

}
