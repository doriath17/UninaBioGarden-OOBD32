package uninabiogarden;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Coltura;

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
  private ProgettoDto progettoDto;

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

  public void init(ProgettoDto progettoDto) {
    this.progettoDto = progettoDto;
    clear();
    initNextStep = true;
    loadColtureDisponibili();
  }

  private void loadColtureDisponibili() {
    availableColtureObsList.setAll(MainController.getInstance().getColtureObservableList());
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
    UIController.getInstance().openCreaProgettoStep2View(progettoDto, false);
  }

  @FXML
  private void nextStepAction() {
    // Prepara i dati del progetto
    progettoDto.coltureIds = selectedColtureObsList.stream().map(Coltura::getId)
        .collect(Collectors.toList());

    Utils.mostraDialogConfermaConAzione(
        "Sei sicuro di voler creare il progetto?",
        progettoDto,
        dto -> {
          MainController.getInstance().creaProgetto(dto);
        });
  }

}
