package uninabiogarden;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Progetto;

public class ControllerCreaAttivita {

  @FXML
  private VBox mainContent;

  @FXML
  private Label nomeProgettoLabel;

  @FXML
  private TextField nomeField;

  @FXML
  private Label dataInizioLabel;

  @FXML
  private DatePicker dataInizioField;

  @FXML
  private DatePicker dataScadenzaField;

  @FXML
  private VBox specificAttributesContent;

  @FXML
  private TableView<Coltivatore> coltivatoriTable;

  @FXML
  private TableColumn<Coltivatore, String> fullNameColumn;

  @FXML
  private TableColumn<Coltivatore, String> usernameColumn;

  @FXML
  private TableColumn<Coltivatore, Void> selectionColumn;

  @FXML
  private Label errorLabel;

  @FXML
  private ChoiceBox<String> tipologiaChoiceBox;

  private ObjectProperty<Coltivatore> selectedColtivatore;

  private Progetto progetto;
  private Coltivazione coltivazione;

  // ==============================================================================================
  // Initialization upon creation
  // ==============================================================================================

  @FXML
  public void initialize() {
    tipologiaChoiceBox.setItems(FXCollections.observableArrayList(
        "Semina", "Irrigazione", "Trattamento", "Raccolta", "Concimazione"));
    setupTable();
  }

  private void setupTable() {
    fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    selectedColtivatore = Utils.<Coltivatore>setupCheckBoxColumnExclusive(selectionColumn);
  }

  // ==============================================================================================
  // Initialization upon opening
  // ==============================================================================================

  public void init(Progetto progetto, Coltivazione coltivazione) {
    clearData();
    this.progetto = progetto;
    this.coltivazione = coltivazione;

    nomeProgettoLabel.setText("Progetto: " + progetto.getNomeProgetto() + " (Coltivazione: "
        + coltivazione.getColtura().getNomeComune() + ")");

    coltivatoriTable.setItems(FXCollections.observableArrayList(progetto.getColtivatori()));
  }

  private void clearData() {
    nomeField.clear();
    dataInizioField.setValue(null);
    dataScadenzaField.setValue(null);
    selectedColtivatore.set(null);
    errorLabel.setText("");
    tipologiaChoiceBox.getSelectionModel().clearSelection();
    specificAttributesContent.getChildren().clear();
  }

  // ==============================================================================================
  // Fetch User Input
  // ==============================================================================================

  private Attivita fetchAttivitaGenerics(Attivita attivita) {
    attivita.setNome(nomeField.getText());
    attivita.setDataInizio(dataInizioField.getValue());
    attivita.setDataScadenza(dataScadenzaField.getValue());
    attivita.setColtivatore(selectedColtivatore.get());
    attivita.setNoteTecniche(""); // TODO: aggiungere campo note tecniche nella UI se serve
    return attivita;
  }

  private Attivita fetchSeminaSpecific() {
    return null;
  }

  private Attivita fetchIrrigazioneSpecific() {
    return null;
  }

  private Attivita fetchTrattamentoSpecific() {
    return null;
  }

  private Attivita fetchRaccoltaSpecific() {
    return null;
  }

  private Attivita fetchConcimazioneSpecific() {
    return null;
  }

  private Attivita fetchUserInput() {
    Attivita attivita = null;

    String tipoSelezionato = tipologiaChoiceBox.getValue();
    if (tipoSelezionato == null) {
      errorLabel.setText("Seleziona una tipologia di attività.");
      return null;
    }
    switch (tipoSelezionato) {
      case "Semina" -> {
        attivita = fetchSeminaSpecific();
      }
      case "Irrigazione" -> {
        attivita = fetchIrrigazioneSpecific();
      }
      case "Trattamento" -> {
        attivita = fetchTrattamentoSpecific();
      }
      case "Raccolta" -> {
        attivita = fetchRaccoltaSpecific();
      }
      case "Concimazione" -> {
        attivita = fetchConcimazioneSpecific();
      }
    }

    attivita = fetchAttivitaGenerics(attivita);
    return attivita;
  }

  // ==============================================================================================
  // Actions
  // ==============================================================================================

  @FXML
  private void pianifica() {

  }

  @FXML
  private void indietroAction() {
    UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione);
  }

  // ==============================================================================================
  // Dynamic Content navigation methods
  // ==============================================================================================

  @FXML
  private void openDettagliSpecifici() {

  }

  @FXML
  private void openNoteTecniche() {

  }

}
