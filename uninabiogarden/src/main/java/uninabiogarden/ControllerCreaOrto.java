package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import uninabiogarden.dto.OrtoDto;

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
  private Label errorLabel;

  @FXML
  public void initialize() {
    errorLabel.setText("");

    Utils.addCharacterLimit(nomeOrtoInputField, 50);
    Utils.addCharacterLimit(cittaInputField, 50);
    Utils.addCharacterLimit(capInputField, 5);
    Utils.addCharacterLimit(viaInputField, 100);
    Utils.addCharacterLimit(civicoInputField, 10);

    loadTestData();
  }

  @FXML
  public void indietroAction() {
    UIController.getInstance().openOrtiView();
  }

  private void loadTestData() {
    nomeOrtoInputField.setText("Orto di Mario");
    cittaInputField.setText("Napoli");
    capInputField.setText("80100");
    viaInputField.setText("Via Roma");
    civicoInputField.setText("10");
  }

  private OrtoDto getData() {
    OrtoDto ortoDto = new OrtoDto();
    ortoDto.nomeOrto = nomeOrtoInputField.getText().trim();
    ortoDto.citta = cittaInputField.getText().trim();
    ortoDto.cap = capInputField.getText().trim();
    ortoDto.via = viaInputField.getText().trim();
    ortoDto.civico = civicoInputField.getText().trim();
    return ortoDto;
  }

  @FXML
  public void confermaAction() {
    OrtoDto ortoDto = getData();
    try {
      MainController.getInstance().creaOrto(ortoDto);
      UIController.getInstance().openOrtiView();
    } catch (IllegalArgumentException e) {
      errorLabel.setText(e.getMessage());
    }
  }
}
