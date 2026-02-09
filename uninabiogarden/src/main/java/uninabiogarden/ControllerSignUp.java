package uninabiogarden;

import javafx.fxml.FXML;

public class ControllerSignUp {

  public ControllerSignUp() {

  }

  public void signUpAction() {

  }

  // TODO: aggiungere un metodo per la clear del form

  @FXML
  public void indietroAction() {
    // TODO: chiamare la clear del form prima di tornare indietro
    UIController.getInstance().openLoginView();
  }

}
