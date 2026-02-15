package uninabiogarden;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Raccolta;

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
  private DatePicker dataInizioField;

  @FXML
  private ChoiceBox<String> statoSaluteChoiceBox;

  @FXML
  private ChoiceBox<String> statoChoiceBox;

  @FXML
  private ChoiceBox<String> statoRaccoltaChoiceBox;

  @FXML
  private TextArea noteTecnicheField;

  @FXML
  private Label errorLabel;
  private Label errorLabelTornaIndietro;

  @FXML
  private Button editButton;

  @FXML
  private TextField nomeRaccoltaField;

  @FXML
  private Label nomeColturaLabel1;

  @FXML
  private DatePicker scadenzaField;

  @FXML
  private TextField qtyPrevistaField;

  @FXML
  private TextField qtyEffettivaField;

  @FXML
  private Label nomeColtivatoreLabel;

  @FXML
  private Label dataPianificazioneLabel;

  private Coltivazione coltivazione;
  private Raccolta raccolta;
  private Progetto progetto;

  @FXML
  private void initialize() {
  }

  public void init(Progetto progetto, Coltivazione coltivazione, Label errorLabel) {
    this.coltivazione = coltivazione;
    this.progetto = progetto;
    this.errorLabelTornaIndietro = errorLabel;
    loadColtivazioneInfo();
    loadRaccoltaInfo();
    toggleEditMode(false);
  }

  private List<String> getAvailableStatiRaccolta() {
    List<String> stati = new ArrayList<>();
    if (raccolta == null) {
      stati.add("PIANIFICATA");
    } else {
      switch (raccolta.getStato()) {
        case PIANIFICATA:
          stati.add("PIANIFICATA");
          stati.add("IN_CORSO");
          break;
        case IN_CORSO:
          stati.add("IN_CORSO");
          stati.add("COMPLETATA");
          break;
        case COMPLETATA:
          stati.add("COMPLETATA");
          break;
      }
    }
    return stati;
  }

  private List<String> getAvailableStatiColtivazione() {
    List<String> stati = new ArrayList<>();
    if (coltivazione.getStato() == null) {
      stati.add("ATTIVA");
    } else {
      switch (coltivazione.getStato()) {
        case ATTIVA:
          stati.add("ATTIVA");
          // Can only transition to IN_RACCOLTA if all activities are complete except
          // raccolta which must be IN_CORSO
          // For now, we allow the transition and validation should be done on save
          stati.add("IN_RACCOLTA");
          break;
        case IN_RACCOLTA:
          stati.add("IN_RACCOLTA");
          // Can only transition to CONCLUSA if raccolta is completed
          stati.add("CONCLUSA");
          break;
        case CONCLUSA:
          // Terminal state - no transitions allowed
          stati.add("CONCLUSA");
          break;
      }
    }
    return stati;
  }

  private void loadColtivazioneInfo() {
    if (coltivazione != null && progetto != null) {
      nomeProgettoLabel.setText(progetto.getNomeProgetto());

      // Setup stato coltivazione with transition restrictions
      statoChoiceBox.setItems(FXCollections.observableArrayList(getAvailableStatiColtivazione()));
      statoChoiceBox.setValue(coltivazione.getStato() != null ? coltivazione.getStato().name() : null);

      // Setup stato salute (no restrictions)
      statoSaluteChoiceBox.setItems(FXCollections.observableArrayList(
          List.of(Coltivazione.StatoSalute.values()).stream().map(Enum::name).toList()));
      statoSaluteChoiceBox
          .setValue(coltivazione.getStatoSalute() != null ? coltivazione.getStatoSalute().name() : null);

      nomeColturaLabel.setText(
          coltivazione.getColtura() != null ? coltivazione.getColtura().getNomeComune() : "N/A");
      tempoMaturazioneLabel.setText(
          coltivazione.getColtura() != null ? coltivazione.getColtura().getTempoMaturazione() + " giorni" : "N/A");

      if (coltivazione.getDataInizio() != null && coltivazione.getColtura() != null) {
        var finePrevista = coltivazione.getDataInizio()
            .plusDays(coltivazione.getColtura().getTempoMaturazione());
        finePrevistaLabel.setText(finePrevista.toString());
      } else {
        finePrevistaLabel.setText("N/A");
      }
    }
  }

  private void loadRaccoltaInfo() {
    this.raccolta = coltivazione.getRaccolta();

    if (raccolta != null) {
      statoRaccoltaChoiceBox.setItems(FXCollections.observableArrayList(getAvailableStatiRaccolta()));
      statoRaccoltaChoiceBox.setValue(raccolta.getStato() != null ? raccolta.getStato().name() : null);

      nomeRaccoltaField.setText(raccolta.getNome());
      dataPianificazioneLabel
          .setText(raccolta.getDataPianificazione() != null ? raccolta.getDataPianificazione().toString() : "N/A");
      scadenzaField.setValue(raccolta.getDataScadenza());
      dataInizioField.setValue(coltivazione.getDataInizio());
      qtyPrevistaField.setText(String.valueOf(raccolta.getQuantitaPrevistaKg()));
      qtyEffettivaField.setText(String.valueOf(raccolta.getQuantitaEffettivaKg()));
      nomeColtivatoreLabel.setText(raccolta.getColtivatore() != null ? raccolta.getColtivatore().getNome() : "N/A");
    }
  }

  private void toggleEditMode(boolean editMode) {
    boolean isConcluded = progetto.getStato() == Progetto.Stato.CONCLUSO;
    boolean isColtivazioneConclusa = coltivazione.getStato() == Coltivazione.Stato.CONCLUSA;

    if (isConcluded || isColtivazioneConclusa) {
      setDisable(true);
      editButton.setDisable(true);
      return;
    } else {
      setDisable(!editMode);
      editButton.setText(editMode ? "Salva" : "Modifica");
    }
  }

  private void setDisable(boolean disable) {
    dataInizioField.setDisable(disable);
    statoSaluteChoiceBox.setDisable(disable);
    statoChoiceBox.setDisable(disable);
    statoRaccoltaChoiceBox.setDisable(disable);
    noteTecnicheField.setEditable(!disable);
    nomeRaccoltaField.setEditable(!disable);
    scadenzaField.setDisable(disable);
    qtyPrevistaField.setDisable(disable);
    qtyEffettivaField.setDisable(disable);
  }

  @FXML
  private void edit(ActionEvent event) {
    if (editButton.getText().equals("Modifica")) {
      toggleEditMode(true);
      return;
    }

    try {

      toggleEditMode(false);

      Utils.showSuccess(errorLabel, "Coltivazione aggiornata con successo");
    } catch (Exception e) {
      Utils.showError(errorLabel, "Errore durante il salvataggio: " + e.getMessage());
    }
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openProgettoColtivazioni(progetto, errorLabelTornaIndietro);
  }

  @FXML
  private void openAttivita() {
    UIController.getInstance().openDettaglioAttivitaView(progetto, coltivazione, errorLabelTornaIndietro);
  }
}
