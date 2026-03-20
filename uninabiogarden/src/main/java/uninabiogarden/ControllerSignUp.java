package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class ControllerSignUp {

  public ControllerSignUp() {

  }

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField emailField;

  @FXML
  private TextField codiceFiscaleField;

  @FXML
  private TextField nomeField;

  @FXML
  private TextField cognomeField;

  @FXML
  private DatePicker bdayField;

  @FXML
  private TextField genderField;

  @FXML
  private TextArea bioField;

  @FXML
  private ChoiceBox<String> tipoUtenteField;

  @FXML
  private Label errorLabel;

  @FXML
  public void initialize() {
    errorLabel.setText("");
    tipoUtenteField.getItems().addAll("COLTIVATORE", "PROPRIETARIO");
    tipoUtenteField.setValue("PROPRIETARIO");

    // character limits for text fields
    Utils.addCharacterLimit(usernameField, 50);
    Utils.addCharacterLimit(passwordField, 50);
    Utils.addCharacterLimit(emailField, 100);
    Utils.addCharacterLimit(codiceFiscaleField, 16);
    Utils.addCharacterLimit(nomeField, 50);
    Utils.addCharacterLimit(cognomeField, 50);
    Utils.addCharacterLimit(genderField, 10);

    loadTestData();

  }

  private Utente getData() {
    String tipo = tipoUtenteField.getValue();
    Utente utente = "COLTIVATORE".equals(tipo) ? new Coltivatore() : new Proprietario();
    utente.setUsername(usernameField.getText());
    utente.setPassword(passwordField.getText());
    utente.setEmail(emailField.getText());
    utente.setCodiceFiscale(codiceFiscaleField.getText());
    utente.setNome(nomeField.getText());
    utente.setCognome(cognomeField.getText());
    utente.setbDay(bdayField.getValue());
    utente.setGender(genderField.getText());
    utente.setBio(bioField.getText());
    return utente;
  }

  private void loadTestData() {
    usernameField.setText("testuser");
    passwordField.setText("password123");
    emailField.setText("testuser@example.com");
    codiceFiscaleField.setText("ABCDEF12G34H567I");
    nomeField.setText("Test");
    cognomeField.setText("User");
    bdayField.setValue(java.time.LocalDate.of(2000, 1, 1));
    genderField.setText("M");
    bioField.setText("This is a test user.");
  }

  @FXML
  public void signUpAction() {
    var utente = getData();
    try {
      MainController.getInstance().registraUtente(utente);
      clearForm();
      if (utente instanceof Coltivatore) {
        UIController.getInstance().openColtivatoreHomeView();
      } else {
        UIController.getInstance().openProprietarioHomeView();
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Dati utente non validi: " + e.getMessage());
      errorLabel.setText("Dati utente non validi: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Errore durante la registrazione: " + e.getMessage());
      errorLabel.setText("Errore durante la registrazione");
    }
  }

  // clear the form fields
  private void clearForm() {
    if (usernameField != null)
      usernameField.clear();
    if (passwordField != null)
      passwordField.clear();
    if (emailField != null)
      emailField.clear();
    if (codiceFiscaleField != null)
      codiceFiscaleField.clear();
    if (nomeField != null)
      nomeField.clear();
    if (cognomeField != null)
      cognomeField.clear();
    if (bdayField != null)
      bdayField.setValue(null);
    if (genderField != null)
      genderField.clear();
    if (bioField != null)
      bioField.clear();
    if (errorLabel != null)
      errorLabel.setText("");
  }

  @FXML
  public void indietroAction() {
    clearForm();
    UIController.getInstance().openLoginView();
  }

}
