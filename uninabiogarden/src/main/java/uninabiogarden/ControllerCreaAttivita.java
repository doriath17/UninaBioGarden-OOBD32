package uninabiogarden;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Concimazione;
import uninabiogarden.entities.Irrigazione;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Raccolta;
import uninabiogarden.entities.Semina;
import uninabiogarden.entities.Trattamento;
import uninabiogarden.exceptions.ValidationException;

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

  // References to dynamic input fields
  private TextField seminaQtyField;
  private TextField seminaProfField;

  private ChoiceBox<String> irrigazioneMetodoBox;
  private TextField irrigazioneVolField;

  private ChoiceBox<String> concimazioneTipoBox;
  private TextField concimazioneQtyField;

  private TextField trattamentoNomeField;
  private TextField trattamentoCarenzaField;

  private TextField raccoltaPrevField;

  private TextArea noteTecnicheArea;

  private enum ContentView {
    NONE, SPECIFICI, NOTE_TECNICHE
  }

  private ContentView currentContentView = ContentView.NONE;

  // ==============================================================================================
  // Initialization upon creation
  // ==============================================================================================

  @FXML
  public void initialize() {
    tipologiaChoiceBox.setItems(FXCollections.observableArrayList(
        "Semina", "Irrigazione", "Trattamento", "Raccolta", "Concimazione"));
    setupTable();
    initDynamicFields();

    // Auto-refresh the specifici panel when the tipologia changes
    tipologiaChoiceBox.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldVal, newVal) -> {
          if (currentContentView == ContentView.SPECIFICI) {
            buildSpecificAttributesView();
          }
        });
  }

  private void setupTable() {
    fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    selectedColtivatore = Utils.<Coltivatore>setupCheckBoxColumnExclusive(selectionColumn);
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

    // Raccolta (solo quantita prevista in fase di creazione)
    raccoltaPrevField = new TextField();
    Utils.addDoubleFilter(raccoltaPrevField, 3, 2);

    // Note tecniche
    noteTecnicheArea = new TextArea();
    noteTecnicheArea.setPrefHeight(140);
    noteTecnicheArea.setWrapText(true);
    Utils.addCharacterLimit(noteTecnicheArea, 1000);
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
    Utils.hideMessage(errorLabel);
    tipologiaChoiceBox.getSelectionModel().clearSelection();
    specificAttributesContent.getChildren().clear();
    currentContentView = ContentView.NONE;

    // Clear dynamic fields
    seminaQtyField.clear();
    seminaProfField.clear();
    irrigazioneMetodoBox.setValue(null);
    irrigazioneVolField.clear();
    concimazioneTipoBox.setValue(null);
    concimazioneQtyField.clear();
    trattamentoNomeField.clear();
    trattamentoCarenzaField.clear();
    raccoltaPrevField.clear();
    noteTecnicheArea.clear();
  }

  // ==============================================================================================
  // Dynamic Content navigation methods
  // ==============================================================================================

  @FXML
  private void openDettagliSpecifici() {
    currentContentView = ContentView.SPECIFICI;
    buildSpecificAttributesView();
  }

  @FXML
  private void openNoteTecniche() {
    currentContentView = ContentView.NOTE_TECNICHE;
    specificAttributesContent.getChildren().clear();
    specificAttributesContent.getChildren().addAll(boldLabel("Note Tecniche"), noteTecnicheArea);
  }

  // ==============================================================================================
  // Dynamic Content build methods
  // ==============================================================================================

  private void buildSpecificAttributesView() {
    specificAttributesContent.getChildren().clear();
    String tipo = tipologiaChoiceBox.getValue();
    if (tipo == null)
      return;

    GridPane grid = makeGrid();
    switch (tipo) {
      case "Semina" -> buildSeminaView(grid);
      case "Irrigazione" -> buildIrrigazioneView(grid);
      case "Concimazione" -> buildConcimazioneView(grid);
      case "Trattamento" -> buildTrattamentoView(grid);
      case "Raccolta" -> buildRaccoltaView(grid);
    }
    specificAttributesContent.getChildren().add(grid);
  }

  private void buildSeminaView(GridPane grid) {
    grid.add(boldLabel("Quantità Sementi"), 0, 0);
    grid.add(seminaQtyField, 1, 0);
    grid.add(boldLabel("Profondità Semina (cm)"), 0, 1);
    grid.add(seminaProfField, 1, 1);
  }

  private void buildIrrigazioneView(GridPane grid) {
    grid.add(boldLabel("Metodo"), 0, 0);
    grid.add(irrigazioneMetodoBox, 1, 0);
    grid.add(boldLabel("Volume Acqua (L)"), 0, 1);
    grid.add(irrigazioneVolField, 1, 1);
  }

  private void buildConcimazioneView(GridPane grid) {
    grid.add(boldLabel("Tipo Concime"), 0, 0);
    grid.add(concimazioneTipoBox, 1, 0);
    grid.add(boldLabel("Quantità (kg)"), 0, 1);
    grid.add(concimazioneQtyField, 1, 1);
  }

  private void buildTrattamentoView(GridPane grid) {
    grid.add(boldLabel("Nome Prodotto"), 0, 0);
    grid.add(trattamentoNomeField, 1, 0);
    grid.add(boldLabel("Tempo Carenza (gg)"), 0, 1);
    grid.add(trattamentoCarenzaField, 1, 1);
  }

  private void buildRaccoltaView(GridPane grid) {
    grid.add(boldLabel("Quantità Prevista (kg)"), 0, 0);
    grid.add(raccoltaPrevField, 1, 0);
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
  // Fetch User Input
  // ==============================================================================================

  private Attivita fetchAttivitaGenerics(Attivita attivita) {
    attivita.setNome(nomeField.getText());
    attivita.setDataInizio(dataInizioField.getValue());
    attivita.setDataScadenza(dataScadenzaField.getValue());
    attivita.setColtivatore(selectedColtivatore.get());
    attivita.setNoteTecniche(noteTecnicheArea.getText());
    return attivita;
  }

  private Attivita fetchSeminaSpecific() {
    Semina semina = new Semina();
    Integer q = parseInteger(seminaQtyField, "Quantità sementi non valida: " + seminaQtyField.getText());
    if (q != null)
      semina.setQuantitaSementi(q);
    Double prof = parseDouble(seminaProfField, "Profondità semina non valida: " + seminaProfField.getText());
    if (prof != null)
      semina.setProfonditaSeminaCm(prof);
    return semina;
  }

  private Attivita fetchIrrigazioneSpecific() {
    Irrigazione irr = new Irrigazione();
    String metodoVal = irrigazioneMetodoBox.getValue();
    if (metodoVal != null)
      irr.setMetodo(Irrigazione.MetodoIrrigazione.valueOf(metodoVal));
    Double vol = parseDouble(irrigazioneVolField, "Volume acqua non valido: " + irrigazioneVolField.getText());
    if (vol != null)
      irr.setVolumeAcquaL(vol);
    return irr;
  }

  private Attivita fetchConcimazioneSpecific() {
    Concimazione conc = new Concimazione();
    String tipoVal = concimazioneTipoBox.getValue();
    if (tipoVal != null)
      conc.setTipoConcime(Concimazione.TipoConcime.valueOf(tipoVal));
    Double q = parseDouble(concimazioneQtyField, "Quantità concime non valida: " + concimazioneQtyField.getText());
    if (q != null)
      conc.setQuantitaKg(q);
    return conc;
  }

  private Attivita fetchTrattamentoSpecific() {
    Trattamento tratt = new Trattamento();
    String nome = trattamentoNomeField.getText();
    if (nome != null && !nome.isBlank())
      tratt.setNomeProdotto(nome);
    Integer car = parseInteger(trattamentoCarenzaField,
        "Tempo carenza non valido: " + trattamentoCarenzaField.getText());
    if (car != null)
      tratt.setTempoCarenza(car);
    return tratt;
  }

  private Attivita fetchRaccoltaSpecific() {
    Raccolta racc = new Raccolta();
    Double prev = parseDouble(raccoltaPrevField, "Quantità prevista non valida: " + raccoltaPrevField.getText());
    if (prev != null)
      racc.setQuantitaPrevistaKg(prev);
    return racc;
  }

  private Attivita fetchUserInput() {
    String tipoSelezionato = tipologiaChoiceBox.getValue();
    if (tipoSelezionato == null) {
      Utils.showError(errorLabel, "Seleziona una tipologia di attività.");
      return null;
    }

    Attivita attivita = switch (tipoSelezionato) {
      case "Semina" -> fetchSeminaSpecific();
      case "Irrigazione" -> fetchIrrigazioneSpecific();
      case "Trattamento" -> fetchTrattamentoSpecific();
      case "Raccolta" -> fetchRaccoltaSpecific();
      case "Concimazione" -> fetchConcimazioneSpecific();
      default -> null;
    };

    if (attivita == null)
      return null;
    return fetchAttivitaGenerics(attivita);
  }

  // ==============================================================================================
  // Actions
  // ==============================================================================================

  @FXML
  private void pianifica() {
    Attivita attivita = fetchUserInput();
    if (attivita == null)
      return;

    try {
      MainController.getInstance().createAttivita(attivita, coltivazione);
      UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione);
    } catch (ValidationException e) {
      Utils.showError(errorLabel, e.getMessage());
    } catch (Exception e) {
      System.err.println("Errore imprevisto durante la pianificazione di una attività: " + e.getMessage());
      Utils.showError(errorLabel, Constants.BASIC_ERROR_MESSAGE);
    }
  }

  @FXML
  private void indietroAction() {
    UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione);
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
