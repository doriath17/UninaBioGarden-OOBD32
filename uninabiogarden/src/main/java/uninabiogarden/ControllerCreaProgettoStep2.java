package uninabiogarden;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Coltivatore;

public class ControllerCreaProgettoStep2 {

  @FXML
  private VBox mainContent;

  @FXML
  private TableView<Coltivatore> availableColtivatoriTable;

  @FXML
  private TableColumn<Coltivatore, String> availableNomeColumn;

  @FXML
  private TableColumn<Coltivatore, String> availableUsernameColumn;

  @FXML
  private TableColumn<Coltivatore, Void> availableSelectionColumn;

  @FXML
  private TableView<Coltivatore> selectedColtivatoriTable;

  @FXML
  private TableColumn<Coltivatore, String> selectedNomeColumn;

  @FXML
  private TableColumn<Coltivatore, String> selectedUsernameColumn;

  @FXML
  private TableColumn<Coltivatore, Void> selectedSelectionColumn;

  @FXML
  private Label errorLabel;

  private ObservableList<Coltivatore> availableColtivatoriObsList = FXCollections.observableArrayList();
  private ObservableList<Coltivatore> selectedColtivatoriObsList = FXCollections.observableArrayList();

  // Mappa per tenere traccia dello stato di selezione di ogni coltivatore, usata
  // per i checkbox nelle tabelle. Le SimpleBooleanProperty permettono di fare il
  // binding con i checkbox in modo che si aggiornino automaticamente quando
  // l'utente seleziona/deseleziona un coltivatore
  private Map<Coltivatore, SimpleBooleanProperty> availableSelectionMap = new HashMap<>();
  private Map<Coltivatore, SimpleBooleanProperty> selectedSelectionMap = new HashMap<>();

  private boolean initNextStep = true;

  ProgettoDto progettoDto;

  @FXML
  private void initialize() {
    clear();

    availableNomeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    Utils.<Coltivatore>setupCheckBoxColumn(availableSelectionColumn, availableSelectionMap);
    availableColtivatoriTable.setItems(availableColtivatoriObsList);

    selectedNomeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    selectedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    Utils.<Coltivatore>setupCheckBoxColumn(selectedSelectionColumn, selectedSelectionMap);
    selectedColtivatoriTable.setItems(selectedColtivatoriObsList);
  }

  public void init(ProgettoDto progettoDto) {
    this.progettoDto = progettoDto;
    clear();
    initNextStep = true;
    loadColtivatoriDisponibili();
  }

  private void loadColtivatoriDisponibili() {
    List<Coltivatore> coltivatoriDisponibili = MainController.getInstance().getColtivatoriDisponibili();
    availableColtivatoriObsList.setAll(coltivatoriDisponibili);
  }

  @FXML
  private void selezionaDisponibili(ActionEvent event) {
    // List<Coltivatore> selectedFromAvailable = availableColtivatoriObsList
    // .stream()
    // .filter(coltivatore -> availableSelectionMap.containsKey(coltivatore)
    // && availableSelectionMap.get(coltivatore).get())
    // .collect(Collectors.toList());

    // // Aggiungi i coltivatori selezionati alla tabella di destinazione
    // selectedColtivatoriObsList.addAll(selectedFromAvailable);

    // // Rimuovi i coltivatori selezionati dalla tabella di origine
    // availableColtivatoriObsList.removeAll(selectedFromAvailable);

    // // Reset delle selezioni dopo il trasferimento
    // selectedFromAvailable.forEach(coltivatore -> {
    // if (availableSelectionMap.containsKey(coltivatore)) {
    // availableSelectionMap.get(coltivatore).set(false);
    // }
    // });
    Utils.<Coltivatore>moveSelectionTo(availableColtivatoriObsList, selectedColtivatoriObsList,
        availableSelectionMap, selectedSelectionMap);
  }

  @FXML
  private void deselezionaSelezionati() {
    // List<Coltivatore> selectedFromSelected = selectedColtivatoriObsList
    // .stream()
    // .filter(coltivatore -> selectedSelectionMap.containsKey(coltivatore)
    // && selectedSelectionMap.get(coltivatore).get())
    // .collect(Collectors.toList());

    // // Aggiungi i coltivatori selezionati alla tabella di destinazione
    // availableColtivatoriObsList.addAll(selectedFromSelected);

    // // Rimuovi i coltivatori selezionati dalla tabella di origine
    // selectedColtivatoriObsList.removeAll(selectedFromSelected);

    // // Reset delle selezioni dopo il trasferimento
    // selectedFromSelected.forEach(coltivatore -> {
    // if (selectedSelectionMap.containsKey(coltivatore)) {
    // selectedSelectionMap.get(coltivatore).set(false);
    // }
    // });
    Utils.<Coltivatore>moveSelectionTo(selectedColtivatoriObsList, availableColtivatoriObsList,
        selectedSelectionMap, availableSelectionMap);
  }

  private void clear() {
    errorLabel.setText("");
    availableColtivatoriObsList.clear();
    selectedColtivatoriObsList.clear();
  }

  @FXML
  private void indietroAction() {
    UIController.getInstance().openCreaProgettoStep1View(progettoDto, false);
  }

  @FXML
  private void nextStepAction() {
    progettoDto.coltivatoriIds = selectedColtivatoriObsList.stream().map(Coltivatore::getId)
        .collect(Collectors.toList());
    for (Long id : progettoDto.coltivatoriIds) {
      System.out.println("Coltivatore selezionato: " + id);
    }
  }

}
