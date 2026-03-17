package uninabiogarden;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltura;
import uninabiogarden.entities.Progetto;

public class ControllerCreaProgettoStep3 {

  @FXML
  private VBox mainContent;

  @FXML
  private Label errorLabel;

  @FXML
  private TableView<Coltura> availableColtureTable;

  @FXML
  private TableColumn<Coltura, String> availableNomeColturaColumn;

  @FXML
  private TableColumn<Coltura, Void> availableSelectionColumn;

  @FXML
  private TableColumn<Coltura, String> selectedNomeColturaColumn;

  @FXML
  private TableColumn<Coltura, Void> selectedSelectionColumn;

  @FXML
  private TableView<Coltura> selectedColtureTable;

  @FXML
  private TableColumn<Coltura, String> availableNomeColturaColumn1;

  @FXML
  private TableColumn<Coltura, Void> availableSelectionColumn1;

  private ObservableList<Coltura> availableColtureObsList = FXCollections.observableArrayList();
  private ObservableList<Coltura> selectedColtureObsList = FXCollections.observableArrayList();

  private Map<Coltura, SimpleBooleanProperty> availableSelectionMap = new HashMap<>();
  private Map<Coltura, SimpleBooleanProperty> selectedSelectionMap = new HashMap<>();

  private boolean initNextStep = true;
  private Progetto nuovoProgetto;

  @FXML
  private void initialize() {
    clear();

    availableNomeColturaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeComune"));
    Utils.<Coltura>setupCheckBoxColumn(availableSelectionColumn, availableSelectionMap);
    availableColtureTable.setItems(availableColtureObsList);

    selectedNomeColturaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeComune"));
    Utils.<Coltura>setupCheckBoxColumn(selectedSelectionColumn, selectedSelectionMap);
    selectedColtureTable.setItems(selectedColtureObsList);

  }

  public void init(Progetto nuovoProgetto) {
    this.nuovoProgetto = nuovoProgetto;
    clear();
    initNextStep = true;
    loadColtureDisponibili();
  }

  private void loadColtureDisponibili() {
    // Crea ObservableList dal model - sincronizzato con la lista delle colture
    ObservableList<Coltura> coltureFromModel = FXCollections.observableList(MainController.getInstance().getColture());
    availableColtureObsList.setAll(coltureFromModel);
  }

  @FXML
  private void selezionaDisponibili() {
    Utils.<Coltura>moveSelectionTo(availableColtureObsList, selectedColtureObsList, availableSelectionMap,
        selectedSelectionMap);
  }

  @FXML
  private void deselezionaSelezionati() {
    Utils.<Coltura>moveSelectionTo(selectedColtureObsList, availableColtureObsList, selectedSelectionMap,
        availableSelectionMap);
  }

  private void clear() {
    errorLabel.setText("");
    availableColtureObsList.clear();
    selectedColtureObsList.clear();
  }

  @FXML
  private void indietroAction() {
    UIController.getInstance().openCreaProgettoStep2View(nuovoProgetto, false);
  }

  @FXML
  private void nextStepAction() {
    // Prepara i dati del progetto
    List<Coltivazione> coltivazioni = selectedColtureObsList.stream().map(coltura -> {
      Coltivazione coltivazione = new Coltivazione();
      coltivazione.setColtura(coltura);
      return coltivazione;
    }).collect(Collectors.toList());
    nuovoProgetto.setColtivazioni(coltivazioni);

    Utils.mostraDialogConfermaConAzione(
        "Sei sicuro di voler creare il progetto?",
        nuovoProgetto,
        dto -> {
          try {
            MainController.getInstance().creaProgetto(dto);
            UIController.getInstance().openProgettiView();
          } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.err.println("Errore durante la creazione del progetto: " + e.getMessage());
            alert.setTitle("Errore");
            alert.setHeaderText("Errore durante la creazione del progetto");
            alert.setContentText("Errore durante la creazione del progetto");
            alert.showAndWait();
          }
        });
  }

}
