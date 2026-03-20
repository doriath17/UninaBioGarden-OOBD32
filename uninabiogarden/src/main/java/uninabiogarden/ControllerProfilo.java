package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;

public class ControllerProfilo {

  @FXML
  private VBox mainContent;

  @FXML
  private DatePicker bdayField;

  @FXML
  private TextArea bioField;

  @FXML
  private TextField codiceFiscaleField;

  @FXML
  private TextField cognomeField;

  @FXML
  private TextField emailField;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField genderField;

  @FXML
  private TextField nomeField;

  @FXML
  private TextField passwordField;

  @FXML
  private ChoiceBox<String> tipoUtenteField;

  @FXML
  private TextField usernameField;

  @FXML
  public void initialize() {

    clearFields();

    errorLabel.setText("");

    tipoUtenteField.getItems().addAll("COLTIVATORE", "PROPRIETARIO");

    usernameField.setText(MainController.getInstance().getUtenteLoggato().getUsername());
    emailField.setText(MainController.getInstance().getUtenteLoggato().getEmail());
    passwordField.setText(MainController.getInstance().getUtenteLoggato().getPassword());
    nomeField.setText(MainController.getInstance().getUtenteLoggato().getNome());
    cognomeField.setText(MainController.getInstance().getUtenteLoggato().getCognome());
    genderField.setText(MainController.getInstance().getUtenteLoggato().getGender());
    bdayField.setValue(MainController.getInstance().getUtenteLoggato().getbDay());
    bioField.setText(MainController.getInstance().getUtenteLoggato().getBio());
    codiceFiscaleField.setText(MainController.getInstance().getUtenteLoggato().getCodiceFiscale());

    bdayField.setDisable(true);
    tipoUtenteField.setDisable(true);

    if (MainController.getInstance().getUtenteLoggato() instanceof Coltivatore) {
      tipoUtenteField.setValue("COLTIVATORE");
    } else {
      tipoUtenteField.setValue("PROPRIETARIO");
    }

  }

  public void clearFields() {
    usernameField.clear();
    emailField.clear();
    passwordField.clear();
    nomeField.clear();
    cognomeField.clear();
    genderField.clear();
    bdayField.setValue(null);
    bioField.clear();
    codiceFiscaleField.clear();
  }

  @FXML
  void indietroAction(ActionEvent event) {
    var isProprietario = MainController.getInstance().getUtenteLoggato() instanceof Proprietario;

    if (isProprietario) {
      UIController.getInstance().openProprietarioHomeView();
    } else {
      UIController.getInstance().openColtivatoreHomeView();
    }
  }

  @FXML
  void logOutAction(ActionEvent event) {
    MainController.getInstance().logout();
    UIController.getInstance().openLoginView();
  }

}
