package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Progetto;

public class ControllerDettaglioProgetto {

  @FXML
  private VBox mainContent;

  @FXML
  private VBox dettaglioContent;

  @FXML
  private Label errorLabel;

  private Progetto currentProgetto;

  @FXML
  private void initialize() {
    errorLabel.setVisible(false);
  }

  public void init(Progetto progetto) {
    this.currentProgetto = progetto;

  }

  VBox getDettaglioContent() {
    return dettaglioContent;
  }

  Label getErrorLabel() {
    return errorLabel;
  }

  @FXML
  private void openInfoGeneraliProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoInfoGenerali(currentProgetto, errorLabel);
  }

  @FXML
  private void openColtivatoriProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoColtivatori(currentProgetto, errorLabel);
  }

  @FXML
  private void openColtivazioniProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoColtivazioni(currentProgetto, errorLabel);
  }

  @FXML
  private void openAttivitaProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoAttivita(currentProgetto, errorLabel);
  }

  @FXML
  private void openNotificheProgetto(ActionEvent event) {
    UIController.getInstance().openProgettoNotifiche(currentProgetto, errorLabel);
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openProgettiView();
  }

}
