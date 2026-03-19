package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ValidationException;

public class ControllerProgettoInfoGenerali {

  @FXML
  private TextField nomeField;

  @FXML
  private TextArea descrizioneField;

  @FXML
  private Label dataInizioLabel;

  @FXML
  private Label dataFineLabel;

  @FXML
  private Label statoProgettoLabel;

  @FXML
  private Button editButton;

  @FXML
  private ChoiceBox<String> statoChoiceBox;

  private Progetto progetto;

  private Label errorLabel;

  @FXML
  private void initialize() {

  }

  public void init(Progetto progetto, Label errorLabel) {
    var isProprietario = MainController.getInstance().getUtenteLoggato() instanceof Proprietario;

    // disabilita per il coltivatore
    editButton.setVisible(isProprietario);
    editButton.setManaged(isProprietario);

    this.progetto = progetto;
    this.errorLabel = errorLabel;
    loadProgettoInfo();
    configureStatoChoiceBox();
    toggleEditMode(false);
    errorLabel.setText("");
  }

  private void loadProgettoInfo() {
    if (progetto != null) {
      nomeField.setText(progetto.getNomeProgetto());
      descrizioneField.setText(progetto.getDescrizione());

      dataInizioLabel.setText(progetto.getDataInizio() != null ? progetto.getDataInizio().toString() : "N/A");
      dataFineLabel.setText(progetto.getDataFine() != null ? progetto.getDataFine().toString() : "N/A");
      statoProgettoLabel.setText(progetto.getStato() != null ? progetto.getStato().name() : "N/A");
    }
  }

  private void configureStatoChoiceBox() {
    // Configura gli stati disponibili in base allo stato attuale
    switch (progetto.getStato()) {
      case ATTIVO:
        statoChoiceBox.setItems(FXCollections.observableArrayList("CONCLUSO"));
        break;
      case CONCLUSO:
        statoChoiceBox.setItems(FXCollections.observableArrayList());
        statoChoiceBox.setDisable(true);
        break;
    }
    statoChoiceBox.getSelectionModel().clearSelection();
  }

  private void toggleEditMode(boolean editMode) {
    nomeField.setEditable(editMode);
    descrizioneField.setEditable(editMode);
    editButton.setText(editMode ? "Salva" : "Modifica");

    boolean isConcluded = progetto.getStato() == Progetto.Stato.CONCLUSO;
    editButton.setDisable(isConcluded);

    if (editButton.getText().equals("Modifica")) {
      statoChoiceBox.setDisable(true);
    } else {
      statoChoiceBox.setDisable(false);
    }
  }

  @FXML
  private void editInfoGeneraliProgetto(ActionEvent event) {
    if (editButton.getText().equals("Modifica")) {
      toggleEditMode(true);
      return;
    }

    try {
      // Aggiorna nome e descrizione
      System.out.println("Salvataggio progetto: " + nomeField.getText() + ", " + descrizioneField.getText());
      MainController.getInstance().updateProgettoInfo(nomeField.getText(), descrizioneField.getText(), progetto);
      System.out.println("Progetto aggiornato: " + progetto.getNomeProgetto() + ", " + progetto.getDescrizione());

      // Aggiorna lo stato se è stato selezionato un nuovo stato
      System.out.println("Stato selezionato: " + statoChoiceBox.getSelectionModel().getSelectedItem());
      String selectedStato = statoChoiceBox.getSelectionModel().getSelectedItem();
      if (selectedStato != null && !selectedStato.isEmpty()) {
        MainController.getInstance().updateProgetto(selectedStato, progetto);
      }
      System.out.println("Progetto dopo aggiornamento stato: " + progetto.getStato());

      // Ricarica le info e riconfigura il choicebox
      loadProgettoInfo();
      configureStatoChoiceBox();
      toggleEditMode(false);

      Utils.showSuccess(errorLabel, "Progetto aggiornato con successo");
    } catch (ValidationException ve) {
      Utils.showError(errorLabel, ve.getMessage());
    } catch (Exception e) {
      Utils.showError(errorLabel, "Errore durante il salvataggio");
    }
  }

}
