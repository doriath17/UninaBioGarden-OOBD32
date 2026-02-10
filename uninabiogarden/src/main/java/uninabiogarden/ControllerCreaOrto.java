package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerCreaOrto {

  @FXML
  private TextField nomeOrtoInputField;

  @FXML
  private TextField cittaInputField;

  @FXML
  private TextField capInputField;

  @FXML
  private TextField viaInputField;

  @FXML
  private TextField civicoInputField;

  @FXML
  public void initialize() {
  }

  @FXML
  public void indietroAction() {
    UIController.getInstance().openOrtiView();
  }

  @FXML
  public void confermaAction() {
  }
}
