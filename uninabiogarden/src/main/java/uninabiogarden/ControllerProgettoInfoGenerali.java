package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerProgettoInfoGenerali {

  @FXML
  private TextField nomeField;

  @FXML
  private TextArea descrizioneField;

  @FXML
  private Label dataCreazioneLabel;

  @FXML
  private Label dataInizioLabel;

  @FXML
  private Label dataFineLabel;

  @FXML
  private ChoiceBox<String> statoProgettoChoiceBox;

  @FXML
  private void initialize() {
    // initialization if needed
  }

  @FXML
  private void editInfoGeneraliProgetto(ActionEvent event) {
    // TODO: implement edit logic
  }

}
