package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerLogIn {

    MainController maincontroller;

    @FXML public TextField usernameField;
    @FXML public TextField passwordField;

    public ControllerLogIn() {
        maincontroller = MainController.getInstance();
    }

    public void newUserAction() {
        maincontroller.cambiaFinestra("SignUpView");
    }

    public void logInAction(){
        maincontroller.logIn();
    }
}
