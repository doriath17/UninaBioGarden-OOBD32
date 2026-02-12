package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControllerDettaglioProgetto {

  @FXML
  private VBox mainContent;

  @FXML
  private VBox dettaglioContent;

  @FXML
  private Label errorLabel;

  @FXML
  private void initialize() {
    errorLabel.setVisible(false);
  }

  VBox getDettaglioContent() {
    return dettaglioContent;
  }

  @FXML
  private void openInfoGeneraliProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoInfoGenerali();
  }

  @FXML
  private void openColtivatoriProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoColtivatori();
  }

  @FXML
  private void openColtivazioniProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoColtivazioni();
  }

  @FXML
  private void openAttivitaProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoAttivita();
  }

  @FXML
  private void openNotificheProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoNotifiche();
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openProgettiView();
  }

}
