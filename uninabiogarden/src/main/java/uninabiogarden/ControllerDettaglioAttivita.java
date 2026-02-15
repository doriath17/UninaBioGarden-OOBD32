package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Progetto;

public class ControllerDettaglioAttivita {

  @FXML
  private Label nomeProgettoLabel;

  @FXML
  private Label tipologiaLabel;

  @FXML
  private Label nomeRaccoltaField;

  @FXML
  private ChoiceBox<String> statoRaccoltaChoiceBox;

  @FXML
  private Label nomeColturaLabel1; // data pianificazione

  @FXML
  private DatePicker dataInizioField;

  @FXML
  private DatePicker scadenzaField;

  @FXML
  private Label nomeColtivatoreLabel;

  @FXML
  private VBox specificAttributesContent;

  @FXML
  private Button pianificaAttivitaButton;

  private Coltivazione coltivazione;
  private Progetto progetto;
  private Label errorLabel;

  @FXML
  private void initialize() {
    // Initialize choice boxes with enum values
    statoRaccoltaChoiceBox.setItems(FXCollections.observableArrayList(
        "PIANIFICATA", "IN_CORSO", "COMPLETATA"));
  }

  public void init(Progetto progetto, Coltivazione coltivazione, Label errorLabel) {
    this.coltivazione = coltivazione;
    this.progetto = progetto;
    this.errorLabel = errorLabel;
    loadAttivitaInfo();
  }

  private void loadAttivitaInfo() {

  }

  private void loadSpecificAttributes() {

  }

  @FXML
  private void indietroAction(ActionEvent event) {
    // Go back to progetto attivita view
    System.out.println("Indietro action - returning to attivita list");
  }

  @FXML
  private void pianificaAttivita(ActionEvent event) {
    // TODO: Implement pianifica attivita logic
    Utils.showError(errorLabel, "Funzionalità non ancora implementata");
  }
}
