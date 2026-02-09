package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class ControllerLogIn {

  @FXML
  Parent contentPane;

  @FXML
  public TextField usernameField;
  @FXML
  public TextField passwordField;

  public ControllerLogIn() {

  }

  @FXML
  public void newUserAction() {
    UIController.getInstance().openSignUpView();
  }

  @FXML
  public void logInAction() {
    UIController.getInstance().openDashboardView();
  }
}
