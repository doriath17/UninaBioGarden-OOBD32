package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import uninabiogarden.entities.Coltivatore;

public class ControllerLogIn {

  @FXML
  Parent contentPane;

  @FXML
  TextField usernameField;
  @FXML
  TextField passwordField;
  @FXML
  Label errorLabel;

  public ControllerLogIn() {

  }

  @FXML
  public void initialize() {
    // test data
    // usernameField.setText("mario_rossi");
    // passwordField.setText("password123");

    // usernameField.setText("luca_n");
    passwordField.setText("password123");

    errorLabel.setText("");
  }

  @FXML
  public void newUserAction() {
    UIController.getInstance().openSignUpView();
  }

  @FXML
  public void logInAction() {
    // get username e password
    String username = usernameField.getText();
    String password = passwordField.getText();
    try {
      MainController.getInstance().loginUtente(username, password);
      if (MainController.getInstance().getUtenteLoggato() instanceof Coltivatore) {
        UIController.getInstance().openColtivatoreHomeView();
      } else {
        UIController.getInstance().openProprietarioHomeView();
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Errore durante il login: " + e.getMessage());
      errorLabel.setText(e.getMessage());
      return;
    }
  }
}
