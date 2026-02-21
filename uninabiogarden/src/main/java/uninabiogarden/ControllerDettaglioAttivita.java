package uninabiogarden;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Concimazione;
import uninabiogarden.entities.Irrigazione;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Raccolta;
import uninabiogarden.entities.Semina;
import uninabiogarden.entities.Trattamento;

public class ControllerDettaglioAttivita {

  @FXML
  private Label nomeProgettoLabel;

  @FXML
  private Label tipologiaLabel;

  @FXML
  private Label nomeAttivitaLabel;

  @FXML
  private Label dataPianificazioneLabel;

  @FXML
  private Label nomeColtivatoreLabel;

  @FXML
  private ChoiceBox<String> statoChoiceBox;

  @FXML
  private DatePicker dataInizioField;

  @FXML
  private DatePicker scadenzaField;

  @FXML
  private VBox specificAttributesContent;

  @FXML
  private Button editButton;

  @FXML
  private Label errorLabel;

  @FXML
  private TableView<Attivita> attivitaTable;

  @FXML
  private TableColumn<Attivita, String> colNome;

  @FXML
  private TableColumn<Attivita, String> colTipologia;

  @FXML
  private TableColumn<Attivita, String> colStato;

  private Progetto progetto;
  private Coltivazione coltivazione;
  private Attivita attivita;
  private Label errorLabelBack;

  private enum ContentView {
    NONE, SPECIFICI, NOTE_TECNICHE
  }

  private ContentView currentContentView = ContentView.NONE;

  @FXML
  private void initialize() {
    setupTable();
  }

  public void init(Progetto progetto, Coltivazione coltivazione, Label errorLabel) {
    this.progetto = progetto;
    this.coltivazione = coltivazione;
    this.errorLabelBack = errorLabel;
    this.attivita = null;
    this.errorLabel.setText("");
    attivitaTable.setItems(FXCollections.observableArrayList(coltivazione.getAttivita()));
    clearDetailPanel();
  }

  private void setupTable() {
    colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));
    colTipologia.setCellValueFactory(cell -> new SimpleStringProperty(getTipologia(cell.getValue())));
    colStato.setCellValueFactory(cell -> new SimpleStringProperty(
        cell.getValue().getStato() != null ? cell.getValue().getStato().name() : ""));
    attivitaTable.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldVal, newVal) -> onAttivitaSelected(newVal));
  }

  private String getTipologia(Attivita a) {
    if (a instanceof Semina)
      return "SEMINA";
    if (a instanceof Irrigazione)
      return "IRRIGAZIONE";
    if (a instanceof Concimazione)
      return "CONCIMAZIONE";
    if (a instanceof Trattamento)
      return "TRATTAMENTO";
    if (a instanceof Raccolta)
      return "RACCOLTA";
    return "N/A";
  }

  private void onAttivitaSelected(Attivita selected) {
    this.attivita = selected;
    this.currentContentView = ContentView.NONE;
    specificAttributesContent.getChildren().clear();
    if (selected == null) {
      clearDetailPanel();
    } else {
      loadAttivitaInfo();
      toggleEditMode(false);
    }
    Utils.hideMessage(errorLabel);
  }

  private void clearDetailPanel() {
    tipologiaLabel.setText("");
    nomeAttivitaLabel.setText("");
    dataPianificazioneLabel.setText("");
    nomeColtivatoreLabel.setText("");
    statoChoiceBox.setItems(FXCollections.observableArrayList());
    statoChoiceBox.setValue(null);
    dataInizioField.setValue(null);
    scadenzaField.setValue(null);
    specificAttributesContent.getChildren().clear();
    editButton.setDisable(true);
    editButton.setText("Modifica");
  }

  private void loadAttivitaInfo() {
    tipologiaLabel.setText(getTipologia(attivita));
    nomeAttivitaLabel.setText(attivita.getNome() != null ? attivita.getNome() : "N/A");
    dataPianificazioneLabel.setText(
        attivita.getDataPianificazione() != null ? attivita.getDataPianificazione().toString() : "N/A");
    nomeColtivatoreLabel.setText(
        attivita.getColtivatore() != null
            ? attivita.getColtivatore().getNome() + " " + attivita.getColtivatore().getCognome()
            : "N/A");
    statoChoiceBox.setItems(FXCollections.observableArrayList(getAvailableStati()));
    statoChoiceBox.setValue(attivita.getStato() != null ? attivita.getStato().name() : null);
    dataInizioField.setValue(attivita.getDataInizio());
    scadenzaField.setValue(attivita.getDataScadenza());

    // Refresh dynamic panel if one is already open
    if (currentContentView == ContentView.SPECIFICI) {
      buildSpecificAttributesView(isInEditMode());
    } else if (currentContentView == ContentView.NOTE_TECNICHE) {
      buildNoteTecnicheView(isInEditMode());
    }
  }

  // ---- Edit mode ----

  private boolean isInEditMode() {
    return "Salva".equals(editButton.getText());
  }

  private boolean isReadOnly() {
    if (attivita == null)
      return true;
    return progetto.getStato() == Progetto.Stato.CONCLUSO
        || coltivazione.getStato() == Coltivazione.Stato.CONCLUSA
        || coltivazione.getStato() == Coltivazione.Stato.IN_RACCOLTA
        || attivita.getStato() == Attivita.Stato.COMPLETATA;
  }

  private void toggleEditMode(boolean editMode) {
    if (attivita == null) {
      editButton.setDisable(true);
      return;
    }
    if (isReadOnly()) {
      setEditable(false);
      editButton.setDisable(true);
    } else {
      setEditable(editMode);
      editButton.setDisable(false);
      editButton.setText(editMode ? "Salva" : "Modifica");
    }
    if (currentContentView == ContentView.SPECIFICI) {
      buildSpecificAttributesView(editMode && !isReadOnly());
    } else if (currentContentView == ContentView.NOTE_TECNICHE) {
      buildNoteTecnicheView(editMode && !isReadOnly());
    }
  }

  private void setEditable(boolean editable) {
    statoChoiceBox.setDisable(!editable);
    dataInizioField.setDisable(!editable);
    scadenzaField.setDisable(!editable);
  }

  private List<String> getAvailableStati() {
    if (attivita == null || attivita.getStato() == null)
      return List.of("PIANIFICATA");
    return switch (attivita.getStato()) {
      case PIANIFICATA -> List.of("PIANIFICATA", "IN_CORSO");
      case IN_CORSO -> List.of("IN_CORSO", "COMPLETATA");
      case COMPLETATA -> List.of("COMPLETATA");
    };
  }

  @FXML
  private void edit(ActionEvent event) {
    if ("Modifica".equals(editButton.getText())) {
      toggleEditMode(true);
      return;
    }
    try {
      // TODO: MainController.getInstance().updateAttivita(...)
      toggleEditMode(false);
      Utils.showSuccess(errorLabel, "Attività aggiornata con successo");
    } catch (Exception e) {
      Utils.showError(errorLabel, e.getMessage());
    }
    loadAttivitaInfo();
    attivitaTable.refresh();
  }

  @FXML
  private void openDettagliSpecifici() {
    if (attivita == null)
      return;
    currentContentView = ContentView.SPECIFICI;
    buildSpecificAttributesView(isInEditMode() && !isReadOnly());
  }

  @FXML
  private void openNoteTecniche() {
    if (attivita == null)
      return;
    currentContentView = ContentView.NOTE_TECNICHE;
    buildNoteTecnicheView(isInEditMode() && !isReadOnly());
  }

  private void buildSpecificAttributesView(boolean editable) {
    specificAttributesContent.getChildren().clear();
    if (attivita == null)
      return;
    GridPane grid = makeGrid();
    if (attivita instanceof Semina semina) {
      buildSeminaView(grid, semina, editable);
    } else if (attivita instanceof Irrigazione irrigazione) {
      buildIrrigazioneView(grid, irrigazione, editable);
    } else if (attivita instanceof Concimazione concimazione) {
      buildConcimazioneView(grid, concimazione, editable);
    } else if (attivita instanceof Trattamento trattamento) {
      buildTrattamentoView(grid, trattamento, editable);
    } else if (attivita instanceof Raccolta raccolta) {
      buildRaccoltaView(grid, raccolta, editable);
    }
    specificAttributesContent.getChildren().add(grid);
  }

  private void buildNoteTecnicheView(boolean editable) {
    specificAttributesContent.getChildren().clear();
    if (attivita == null)
      return;
    Label title = boldLabel("Note Tecniche");
    TextArea area = new TextArea(attivita.getNoteTecniche() != null ? attivita.getNoteTecniche() : "");
    area.setEditable(editable);
    area.setPrefHeight(140);
    area.setWrapText(true);
    Utils.addCharacterLimit(area, 1000);
    specificAttributesContent.getChildren().addAll(title, area);
  }

  // SEMINA: quantita_sementi INT > 0, profondita_semina_cm DECIMAL(4,2) optional
  // [0, 50)
  private void buildSeminaView(GridPane grid, Semina semina, boolean editable) {
    grid.add(boldLabel("Quantità Sementi"), 0, 0);
    grid.add(boldLabel("Profondità Semina (cm)"), 0, 1);
    if (editable) {
      TextField qtyField = new TextField(String.valueOf(semina.getQuantitaSementi()));
      Utils.addDoubleFilter(qtyField, 6, 0); // INT > 0
      grid.add(qtyField, 1, 0);
      TextField profField = new TextField(
          semina.getProfonditaSeminaCm() != null ? String.valueOf(semina.getProfonditaSeminaCm()) : "");
      Utils.addDoubleFilter(profField, 2, 2); // DECIMAL(4,2): max 49.99
      grid.add(profField, 1, 1);
    } else {
      grid.add(valueLabel(String.valueOf(semina.getQuantitaSementi())), 1, 0);
      grid.add(valueLabel(
          semina.getProfonditaSeminaCm() != null ? semina.getProfonditaSeminaCm() + " cm" : "N/A"), 1, 1);
    }
  }

  // IRRIGAZIONE: metodo enum, volume_acqua_l DECIMAL(5,2) optional > 0
  private void buildIrrigazioneView(GridPane grid, Irrigazione irrigazione, boolean editable) {
    grid.add(boldLabel("Metodo"), 0, 0);
    grid.add(boldLabel("Volume Acqua (L)"), 0, 1);
    if (editable) {
      ChoiceBox<String> metodoBox = new ChoiceBox<>(FXCollections.observableArrayList(
          List.of(Irrigazione.MetodoIrrigazione.values()).stream().map(Enum::name).toList()));
      metodoBox.setValue(irrigazione.getMetodo() != null ? irrigazione.getMetodo().name() : null);
      grid.add(metodoBox, 1, 0);
      TextField volField = new TextField(
          irrigazione.getVolumeAcquaL() != null ? String.valueOf(irrigazione.getVolumeAcquaL()) : "");
      Utils.addDoubleFilter(volField, 3, 2); // DECIMAL(5,2): 3 int digits, 2 frac
      grid.add(volField, 1, 1);
    } else {
      grid.add(valueLabel(irrigazione.getMetodo() != null ? irrigazione.getMetodo().name() : "N/A"), 1, 0);
      grid.add(valueLabel(
          irrigazione.getVolumeAcquaL() != null ? irrigazione.getVolumeAcquaL() + " L" : "N/A"), 1, 1);
    }
  }

  // CONCIMAZIONE: tipo_concime enum, quantita_kg DECIMAL(5,2) > 0
  private void buildConcimazioneView(GridPane grid, Concimazione concimazione, boolean editable) {
    grid.add(boldLabel("Tipo Concime"), 0, 0);
    grid.add(boldLabel("Quantità (kg)"), 0, 1);
    if (editable) {
      ChoiceBox<String> tipoBox = new ChoiceBox<>(FXCollections.observableArrayList(
          List.of(Concimazione.TipoConcime.values()).stream().map(Enum::name).toList()));
      tipoBox.setValue(concimazione.getTipoConcime() != null ? concimazione.getTipoConcime().name() : null);
      grid.add(tipoBox, 1, 0);
      TextField qtyField = new TextField(String.valueOf(concimazione.getQuantitaKg()));
      Utils.addDoubleFilter(qtyField, 3, 2); // DECIMAL(5,2)
      grid.add(qtyField, 1, 1);
    } else {
      grid.add(valueLabel(
          concimazione.getTipoConcime() != null ? concimazione.getTipoConcime().name() : "N/A"), 1, 0);
      grid.add(valueLabel(concimazione.getQuantitaKg() + " kg"), 1, 1);
    }
  }

  // TRATTAMENTO: nome_prodotto VARCHAR(50) > 0, tempo_carenza INT optional > 0
  private void buildTrattamentoView(GridPane grid, Trattamento trattamento, boolean editable) {
    grid.add(boldLabel("Nome Prodotto"), 0, 0);
    grid.add(boldLabel("Tempo Carenza (gg)"), 0, 1);
    if (editable) {
      TextField nomeField = new TextField(
          trattamento.getNomeProdotto() != null ? trattamento.getNomeProdotto() : "");
      Utils.addCharacterLimit(nomeField, 50); // VARCHAR(50)
      grid.add(nomeField, 1, 0);
      TextField carenzaField = new TextField(
          trattamento.getTempoCarenza() != null ? String.valueOf(trattamento.getTempoCarenza()) : "");
      Utils.addDoubleFilter(carenzaField, 6, 0); // INT > 0
      grid.add(carenzaField, 1, 1);
    } else {
      grid.add(valueLabel(
          trattamento.getNomeProdotto() != null ? trattamento.getNomeProdotto() : "N/A"), 1, 0);
      grid.add(valueLabel(
          trattamento.getTempoCarenza() != null ? trattamento.getTempoCarenza() + " gg" : "N/A"), 1, 1);
    }
  }

  // RACCOLTA: quantita_prevista_kg DECIMAL(5,2) > 0, quantita_effettiva_kg
  // DECIMAL(5,2) optional
  private void buildRaccoltaView(GridPane grid, Raccolta raccolta, boolean editable) {
    grid.add(boldLabel("Quantità Prevista (kg)"), 0, 0);
    grid.add(boldLabel("Quantità Effettiva (kg)"), 0, 1);
    if (editable) {
      TextField prevField = new TextField(String.valueOf(raccolta.getQuantitaPrevistaKg()));
      Utils.addDoubleFilter(prevField, 3, 2); // DECIMAL(5,2) > 0
      grid.add(prevField, 1, 0);
      TextField effField = new TextField(
          raccolta.getQuantitaEffettivaKg() != null ? String.valueOf(raccolta.getQuantitaEffettivaKg()) : "");
      Utils.addDoubleFilter(effField, 3, 2); // DECIMAL(5,2) optional
      grid.add(effField, 1, 1);
    } else {
      grid.add(valueLabel(raccolta.getQuantitaPrevistaKg() + " kg"), 1, 0);
      grid.add(valueLabel(
          raccolta.getQuantitaEffettivaKg() != null
              ? raccolta.getQuantitaEffettivaKg() + " kg"
              : "N/A"),
          1, 1);
    }
  }

  private GridPane makeGrid() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(8);
    grid.setPadding(new Insets(8, 0, 0, 0));
    ColumnConstraints c1 = new ColumnConstraints();
    c1.setPercentWidth(45);
    ColumnConstraints c2 = new ColumnConstraints();
    c2.setPercentWidth(55);
    grid.getColumnConstraints().addAll(c1, c2);
    return grid;
  }

  private Label boldLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font("System Bold", 14));
    return label;
  }

  private Label valueLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font(13));
    return label;
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione, errorLabelBack);
  }

  @FXML
  private void pianificaAttivita(ActionEvent event) {
    Utils.showError(errorLabel, "Funzionalità non ancora implementata");
  }
}
