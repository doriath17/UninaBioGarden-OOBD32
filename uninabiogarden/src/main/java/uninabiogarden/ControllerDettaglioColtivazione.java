package uninabiogarden;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Progetto;

public class ControllerDettaglioColtivazione {

  @FXML
  private Label nomeProgettoLabel;

  @FXML
  private Label nomeColturaLabel;

  @FXML
  private Label tempoMaturazioneLabel;

  @FXML
  private Label finePrevistaLabel;

  @FXML
  private Label dataInizioLabel;

  @FXML
  private ChoiceBox<String> statoSaluteChoiceBox;

  @FXML
  private Label statoColtivazioneLabel;

  @FXML
  private TextArea noteTecnicheField;

  @FXML
  private Label errorLabel;

  @FXML
  private Button editButton;

  private Coltivazione coltivazione;
  private Progetto progetto;

  @FXML
  private void initialize() {
    Utils.addCharacterLimit(noteTecnicheField, 500);
  }

  public void init(Progetto progetto, Coltivazione coltivazione) {
    this.coltivazione = coltivazione;
    this.progetto = progetto;
    loadColtivazioneInfo();
    toggleEditMode(false);
    Utils.hideMessage(errorLabel);
  }

  private void loadColtivazioneInfo() {
    if (coltivazione != null && progetto != null) {
      nomeProgettoLabel.setText(progetto.getNomeProgetto());

      nomeColturaLabel.setText(
          coltivazione.getColtura() != null ? coltivazione.getColtura().getNomeComune() : "N/A");

      dataInizioLabel.setText(coltivazione.getDataInizio() != null ? coltivazione.getDataInizio().toString() : "N/A");

      tempoMaturazioneLabel.setText(
          coltivazione.getColtura() != null ? coltivazione.getColtura().getTempoMaturazione() + " giorni" : "N/A");

      if (coltivazione.getDataInizio() != null && coltivazione.getColtura() != null) {
        finePrevistaLabel.setText(coltivazione.getDataFinePrevista().toString());
      } else {
        finePrevistaLabel.setText("N/A");
      }

      statoColtivazioneLabel.setText(coltivazione.getStato().name());

      statoSaluteChoiceBox.setItems(FXCollections.observableArrayList(
          List.of(Coltivazione.StatoSalute.values()).stream().map(Enum::name).toList()));
      statoSaluteChoiceBox
          .setValue(coltivazione.getStatoSalute() != null ? coltivazione.getStatoSalute().name() : null);

      noteTecnicheField.setText(coltivazione.getNoteTecniche());
    }
  }

  private void toggleEditMode(boolean editMode) {
    boolean isReadOnly = progetto.getStato() == Progetto.Stato.CONCLUSO
        || coltivazione.getStato() == Coltivazione.Stato.CONCLUSA
        || coltivazione.getStato() == Coltivazione.Stato.IN_RACCOLTA;

    if (isReadOnly) {
      setEditable(false);
      editButton.setText("Modifica");
      editButton.setDisable(true);
    } else {
      setEditable(editMode);
      editButton.setText(editMode ? "Salva" : "Modifica");
    }
  }

  private void setEditable(boolean editable) {
    statoSaluteChoiceBox.setDisable(!editable);
    noteTecnicheField.setEditable(editable);
  }

  @FXML
  private void edit(ActionEvent event) {
    if (editButton.getText().equals("Modifica")) {
      toggleEditMode(true);
      return;
    }

    try {
      toggleEditMode(false);
      var nuovoStatoSalute = Coltivazione.StatoSalute.valueOf(statoSaluteChoiceBox.getValue());
      var nuoveNoteTecniche = noteTecnicheField.getText();
      MainController.getInstance().updateColtivazione(nuovoStatoSalute, nuoveNoteTecniche, coltivazione);
      Utils.showSuccess(errorLabel, "Coltivazione aggiornata con successo");
    } catch (Exception e) {
      Utils.showError(errorLabel, e.getMessage());
    }
    loadColtivazioneInfo();
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().backToProgettoColtivazioni(progetto);
  }

  @FXML
  private void openAttivita() {
    UIController.getInstance().openDettaglioAttivitaView(progetto, coltivazione);
  }
}
