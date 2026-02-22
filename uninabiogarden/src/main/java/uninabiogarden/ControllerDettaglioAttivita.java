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
import uninabiogarden.exceptions.ValidationException;

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

  // References to dynamic input fields created in the specific-attributes views
  private TextField seminaQtyField;
  private TextField seminaProfField;

  private ChoiceBox<String> irrigazioneMetodoBox;
  private TextField irrigazioneVolField;

  private ChoiceBox<String> concimazioneTipoBox;
  private TextField concimazioneQtyField;

  private TextField trattamentoNomeField;
  private TextField trattamentoCarenzaField;

  private TextField raccoltaPrevField;
  private TextField raccoltaEffField;

  private TextArea noteTecnicheArea;

  private enum ContentView {
    NONE, SPECIFICI, NOTE_TECNICHE
  }

  private ContentView currentContentView = ContentView.NONE;

  // ==============================================================================================
  // Initialization upon creation
  // ==============================================================================================

  @FXML
  private void initialize() {
    setupTable();
    initDynamicFields();
  }

  private void initDynamicFields() {
    // Semina
    seminaQtyField = new TextField();
    Utils.addDoubleFilter(seminaQtyField, 6, 0);
    seminaProfField = new TextField();
    Utils.addDoubleFilter(seminaProfField, 2, 2);

    // Irrigazione
    irrigazioneMetodoBox = new ChoiceBox<>(FXCollections.observableArrayList(
        List.of(Irrigazione.MetodoIrrigazione.values()).stream().map(Enum::name).toList()));
    irrigazioneVolField = new TextField();
    Utils.addDoubleFilter(irrigazioneVolField, 3, 2);

    // Concimazione
    concimazioneTipoBox = new ChoiceBox<>(FXCollections.observableArrayList(
        List.of(Concimazione.TipoConcime.values()).stream().map(Enum::name).toList()));
    concimazioneQtyField = new TextField();
    Utils.addDoubleFilter(concimazioneQtyField, 3, 2);

    // Trattamento
    trattamentoNomeField = new TextField();
    Utils.addCharacterLimit(trattamentoNomeField, 50);
    trattamentoCarenzaField = new TextField();
    Utils.addDoubleFilter(trattamentoCarenzaField, 6, 0);

    // Raccolta
    raccoltaPrevField = new TextField();
    Utils.addDoubleFilter(raccoltaPrevField, 3, 2);
    raccoltaEffField = new TextField();
    Utils.addDoubleFilter(raccoltaEffField, 3, 2);

    // Note tecniche
    noteTecnicheArea = new TextArea();
    noteTecnicheArea.setPrefHeight(140);
    noteTecnicheArea.setWrapText(true);
    Utils.addCharacterLimit(noteTecnicheArea, 1000);
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
      clearData();
    } else {
      loadAttivitaInfo();
      toggleEditMode(false, selected);
    }
    Utils.hideMessage(errorLabel);
  }

  // ==============================================================================================
  // Initialization upon opening
  // ==============================================================================================

  public void init(Progetto progetto, Coltivazione coltivazione) {
    this.progetto = progetto;
    this.coltivazione = coltivazione;
    this.attivita = null;
    this.errorLabel.setText("");
    attivitaTable.setItems(FXCollections.observableArrayList(coltivazione.getAttivita()));
    clearData();
  }

  public void init(Progetto progetto, Coltivazione coltivazione, Attivita selectedAttivita) {
    init(progetto, coltivazione);
    if (selectedAttivita != null) {
      attivitaTable.getSelectionModel().select(selectedAttivita);
      attivitaTable.scrollTo(selectedAttivita);
    }
  }

  private void clearData() {
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
    populateDynamicFields(attivita);

    // Refresh dynamic panel if one is already open
    if (currentContentView == ContentView.SPECIFICI) {
      buildSpecificAttributesView(isInEditMode());
    } else if (currentContentView == ContentView.NOTE_TECNICHE) {
      buildNoteTecnicheView(isInEditMode());
    }
  }

  private void populateDynamicFields(Attivita a) {
    noteTecnicheArea.setText(a.getNoteTecniche() != null ? a.getNoteTecniche() : "");
    if (a instanceof Semina semina) {
      seminaQtyField.setText(String.valueOf(semina.getQuantitaSementi()));
      seminaProfField.setText(
          semina.getProfonditaSeminaCm() != null ? String.valueOf(semina.getProfonditaSeminaCm()) : "");
    } else if (a instanceof Irrigazione irr) {
      irrigazioneMetodoBox.setValue(irr.getMetodo() != null ? irr.getMetodo().name() : null);
      irrigazioneVolField.setText(
          irr.getVolumeAcquaL() != null ? String.valueOf(irr.getVolumeAcquaL()) : "");
    } else if (a instanceof Concimazione conc) {
      concimazioneTipoBox.setValue(conc.getTipoConcime() != null ? conc.getTipoConcime().name() : null);
      concimazioneQtyField.setText(
          conc.getQuantitaKg() != null ? String.valueOf(conc.getQuantitaKg()) : "");
    } else if (a instanceof Trattamento tratt) {
      trattamentoNomeField.setText(tratt.getNomeProdotto() != null ? tratt.getNomeProdotto() : "");
      trattamentoCarenzaField.setText(
          tratt.getTempoCarenza() != null ? String.valueOf(tratt.getTempoCarenza()) : "");
    } else if (a instanceof Raccolta racc) {
      raccoltaPrevField.setText(
          racc.getQuantitaPrevistaKg() != null ? String.valueOf(racc.getQuantitaPrevistaKg()) : "");
      raccoltaEffField.setText(
          racc.getQuantitaEffettivaKg() != null ? String.valueOf(racc.getQuantitaEffettivaKg()) : "");
    }
  }

  // ==============================================================================================
  // Handling edit mode and read only state
  // ==============================================================================================

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

  private void toggleEditMode(boolean editMode, Attivita attivita) {
    if (attivita == null) {
      editButton.setDisable(true);
      return;
    }
    if (isReadOnly()) {
      setEditable(false, attivita);
      editButton.setDisable(true);
    } else {
      setEditable(editMode, attivita);
      editButton.setDisable(false);
      editButton.setText(editMode ? "Salva" : "Modifica");
    }
    if (currentContentView == ContentView.SPECIFICI) {
      buildSpecificAttributesView(editMode && !isReadOnly());
    } else if (currentContentView == ContentView.NOTE_TECNICHE) {
      buildNoteTecnicheView(editMode && !isReadOnly());
    }
  }

  private void setEditable(boolean editable, Attivita attivita) {
    statoChoiceBox.setDisable(!editable);
    if (editable && (attivita.getStato() != Attivita.Stato.PIANIFICATA)) {
      dataInizioField.setDisable(true);
    } else {
      dataInizioField.setDisable(!editable);
    }
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

  // ==============================================================================================
  // Methods to load user inputs into DTOs and basic validation
  // ==============================================================================================

  private Attivita getDataAttivita(Attivita dto) {
    Attivita.Stato newStato = Attivita.Stato.valueOf(statoChoiceBox.getValue());
    dto.setStato(newStato);
    dto.setDataInizio(dataInizioField.getValue());
    dto.setDataScadenza(scadenzaField.getValue());

    if (noteTecnicheArea != null)
      dto.setNoteTecniche(noteTecnicheArea.getText());

    return dto;
  }

  private Semina getDataSemina() {
    Semina dto = new Semina((Semina) attivita);
    getDataAttivita(dto);
    Integer q = parseInteger(seminaQtyField, "Valore di quantità di semi non valido: " + seminaQtyField.getText());
    if (q != null)
      dto.setQuantitaSementi(q);

    Double prof = parseDouble(seminaProfField,
        "Valore di profondità di semina non valido: " + seminaProfField.getText());
    if (prof != null)
      dto.setProfonditaSeminaCm(prof);

    return dto;
  }

  private Concimazione getDataConcimazione() {
    Concimazione dto = new Concimazione((Concimazione) attivita);
    getDataAttivita(dto);

    Concimazione.TipoConcime tipo = Concimazione.TipoConcime.valueOf(concimazioneTipoBox.getValue());
    if (tipo != null)
      dto.setTipoConcime(tipo);

    Double q = parseDouble(concimazioneQtyField,
        "Valore di quantità di concime non valido: " + concimazioneQtyField.getText());
    if (q != null)
      dto.setQuantitaKg(q);

    return dto;
  }

  private Irrigazione getDataIrrigazione() {
    Irrigazione dto = new Irrigazione((Irrigazione) attivita);
    getDataAttivita(dto);

    Irrigazione.MetodoIrrigazione metodo = Irrigazione.MetodoIrrigazione.valueOf(irrigazioneMetodoBox.getValue());
    if (metodo != null)
      dto.setMetodo(metodo);

    Double vol = parseDouble(irrigazioneVolField,
        "Valore di volume d'acqua non valido: " + irrigazioneVolField.getText());
    if (vol != null)
      dto.setVolumeAcquaL(vol);

    return dto;
  }

  private Trattamento getDataTrattamento() {
    Trattamento dto = new Trattamento((Trattamento) attivita);
    getDataAttivita(dto);

    if (trattamentoNomeField != null && trattamentoNomeField.getText() != null
        && !trattamentoNomeField.getText().isBlank())
      dto.setNomeProdotto(trattamentoNomeField.getText());

    Integer car = parseInteger(trattamentoCarenzaField,
        "Valore di tempo di carenza non valido: " + trattamentoCarenzaField.getText());
    if (car != null)
      dto.setTempoCarenza(car);

    return dto;
  }

  private Raccolta getDataRaccolta() {
    Raccolta dto = new Raccolta((Raccolta) attivita);
    getDataAttivita(dto);

    Double prev = parseDouble(raccoltaPrevField,
        "Valore di previsione raccolta non valido: " + raccoltaPrevField.getText());
    if (prev != null)
      dto.setQuantitaPrevistaKg(prev);

    Double eff = parseDouble(raccoltaEffField,
        "Valore di raccolta effettiva non valido: " + raccoltaEffField.getText());
    if (eff != null)
      dto.setQuantitaEffettivaKg(eff);

    return dto;
  }

  private Attivita getData() {
    if (attivita == null) {
      return null;
    }
    Attivita dto = null;

    if (attivita instanceof Semina) {
      dto = getDataSemina();
    } else if (attivita instanceof Irrigazione) {
      dto = getDataIrrigazione();
    } else if (attivita instanceof Concimazione) {
      dto = getDataConcimazione();
    } else if (attivita instanceof Trattamento) {
      dto = getDataTrattamento();
    } else if (attivita instanceof Raccolta) {
      dto = getDataRaccolta();
    }

    return dto;
  }

  @FXML
  private void edit(ActionEvent event) {
    if ("Modifica".equals(editButton.getText())) {
      toggleEditMode(true, attivita);
      return;
    }
    try {
      Attivita dto = getData();
      MainController.getInstance().updateAttivita(dto, attivita, coltivazione);
      toggleEditMode(false, dto);
      loadAttivitaInfo();
      attivitaTable.refresh();
      Utils.showSuccess(errorLabel, "Attività aggiornata con successo");
    } catch (ValidationException ve) {
      Utils.showError(errorLabel, ve.getMessage());
    } catch (Exception e) {
      System.err.println("Errore aggiornamento attività: " + e.getMessage());
      Utils.showError(errorLabel, Constants.BASIC_ERROR_MESSAGE);
    }
  }

  // ==============================================================================================
  // Dynamic Content navigation methods
  // ==============================================================================================

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

  // ==============================================================================================
  // Dynamic Content methods
  // ==============================================================================================

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
    noteTecnicheArea.setEditable(editable);
    specificAttributesContent.getChildren().addAll(boldLabel("Note Tecniche"), noteTecnicheArea);
  }

  // SEMINA: quantita_sementi INT > 0, profondita_semina_cm DECIMAL(4,2) optional
  // [0, 50)
  private void buildSeminaView(GridPane grid, Semina semina, boolean editable) {
    seminaQtyField.setDisable(!editable);
    seminaProfField.setDisable(!editable);
    grid.add(boldLabel("Quantità Sementi"), 0, 0);
    grid.add(seminaQtyField, 1, 0);
    grid.add(boldLabel("Profondità Semina (cm)"), 0, 1);
    grid.add(seminaProfField, 1, 1);
  }

  // IRRIGAZIONE: metodo enum, volume_acqua_l DECIMAL(5,2) optional > 0
  private void buildIrrigazioneView(GridPane grid, Irrigazione irrigazione, boolean editable) {
    irrigazioneMetodoBox.setDisable(!editable);
    irrigazioneVolField.setDisable(!editable);
    grid.add(boldLabel("Metodo"), 0, 0);
    grid.add(irrigazioneMetodoBox, 1, 0);
    grid.add(boldLabel("Volume Acqua (L)"), 0, 1);
    grid.add(irrigazioneVolField, 1, 1);
  }

  // CONCIMAZIONE: tipo_concime enum, quantita_kg DECIMAL(5,2) > 0
  private void buildConcimazioneView(GridPane grid, Concimazione concimazione, boolean editable) {
    concimazioneTipoBox.setDisable(!editable);
    concimazioneQtyField.setDisable(!editable);
    grid.add(boldLabel("Tipo Concime"), 0, 0);
    grid.add(concimazioneTipoBox, 1, 0);
    grid.add(boldLabel("Quantità (kg)"), 0, 1);
    grid.add(concimazioneQtyField, 1, 1);
  }

  // TRATTAMENTO: nome_prodotto VARCHAR(50) > 0, tempo_carenza INT optional > 0
  private void buildTrattamentoView(GridPane grid, Trattamento trattamento, boolean editable) {
    trattamentoNomeField.setDisable(!editable);
    trattamentoCarenzaField.setDisable(!editable);
    grid.add(boldLabel("Nome Prodotto"), 0, 0);
    grid.add(trattamentoNomeField, 1, 0);
    grid.add(boldLabel("Tempo Carenza (gg)"), 0, 1);
    grid.add(trattamentoCarenzaField, 1, 1);
  }

  // RACCOLTA: quantita_prevista_kg DECIMAL(5,2) > 0, quantita_effettiva_kg
  // DECIMAL(5,2) optional
  private void buildRaccoltaView(GridPane grid, Raccolta raccolta, boolean editable) {
    boolean isPianificata = raccolta.getStato() == Attivita.Stato.PIANIFICATA;
    boolean isInCorso = raccolta.getStato() == Attivita.Stato.IN_CORSO;
    // quantita_prevista is only editable when PIANIFICATA
    raccoltaPrevField.setDisable(!editable || !isPianificata);
    // quantita_effettiva is only editable when IN_CORSO; always disabled when
    // PIANIFICATA
    raccoltaEffField.setDisable(!editable || !isInCorso);
    grid.add(boldLabel("Quantità Prevista (kg)"), 0, 0);
    grid.add(raccoltaPrevField, 1, 0);
    grid.add(boldLabel("Quantità Effettiva (kg)"), 0, 1);
    grid.add(raccoltaEffField, 1, 1);
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

  // ==============================================================================================
  // Navigation methods
  // ==============================================================================================

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione);
  }

  @FXML
  private void pianificaAttivita(ActionEvent event) {
    UIController.getInstance().openPianificazioneAttivita(progetto, coltivazione);
  }

  // ==============================================================================================
  // Helper methods
  // ==============================================================================================

  private Integer parseInteger(TextField f, String errorMessage) {
    if (f == null)
      return null;
    String s = f.getText();
    if (s == null || s.isBlank())
      return null;
    try {
      return Integer.valueOf(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  private Double parseDouble(TextField f, String errorMessage) {
    if (f == null)
      return null;
    String s = f.getText();
    if (s == null || s.isBlank())
      return null;
    try {
      return Double.valueOf(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

}
