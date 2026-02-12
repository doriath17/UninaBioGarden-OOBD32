package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Coltivatore;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

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
    setupCheckBoxColumn(availableSelectionColumn, availableSelectionMap);
    availableColtivatoriTable.setItems(availableColtivatoriObsList);

    selectedNomeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    selectedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    setupCheckBoxColumn(selectedSelectionColumn, selectedSelectionMap);
    selectedColtivatoriTable.setItems(selectedColtivatoriObsList);
  }

  private void setupCheckBoxColumn(TableColumn<Coltivatore, Void> column,
      Map<Coltivatore, SimpleBooleanProperty> selectionMap) {
    column.setCellFactory(col -> new TableCell<Coltivatore, Void>() {
      private final CheckBox checkBox = new CheckBox();
      private SimpleBooleanProperty currentProperty = null;

      {
        // Initializza il checkbox e aggiungi listener per aggiornare la mappa di
        // selezione quando viene cliccato
        checkBox.setOnAction(event -> {
          Coltivatore coltivatore = getTableRow().getItem();
          if (coltivatore != null) {
            // Aggiorna lo stato di selezione nella mappa
            selectionMap.get(coltivatore).set(checkBox.isSelected());
          }
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (currentProperty != null) {
          // Se c'è una proprietà attualmente associata al checkbox, rimuovi il binding
          checkBox.selectedProperty().unbindBidirectional(currentProperty);
          currentProperty = null;
        }

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setGraphic(null);
        } else {
          Coltivatore coltivatore = getTableRow().getItem();

          // Crea la SimpleBooleanProperty se non esiste
          if (!selectionMap.containsKey(coltivatore)) {
            selectionMap.put(coltivatore, new SimpleBooleanProperty(false));
          }

          // Qui le SimpleBooleanProperty vengono "colleggate" al checkbox con un binding
          // bidirezionale che singnifica che se l'utente clicca il checkbox, la proprietà
          // si aggiorna, e se la proprietà viene aggiornata (ad esempio quando si sposta
          // un coltivatore da una tabella all'altra), il checkbox si aggiorna di
          // conseguenza. In questo modo lo stato di selezione rimane sempre sincronizzato
          // tra la UI e la logica dell'applicazione.
          currentProperty = selectionMap.get(coltivatore);
          checkBox.selectedProperty().bindBidirectional(currentProperty);

          setGraphic(checkBox);
        }
      }
    });
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
    List<Coltivatore> selectedFromAvailable = availableColtivatoriObsList
        .stream()
        .filter(coltivatore -> availableSelectionMap.containsKey(coltivatore)
            && availableSelectionMap.get(coltivatore).get())
        .collect(Collectors.toList());

    // Aggiungi i coltivatori selezionati alla tabella di destinazione
    selectedColtivatoriObsList.addAll(selectedFromAvailable);

    // Rimuovi i coltivatori selezionati dalla tabella di origine
    availableColtivatoriObsList.removeAll(selectedFromAvailable);

    // Reset delle selezioni dopo il trasferimento
    selectedFromAvailable.forEach(coltivatore -> {
      if (availableSelectionMap.containsKey(coltivatore)) {
        availableSelectionMap.get(coltivatore).set(false);
      }
    });
  }

  @FXML
  private void deselezionaSelezionati() {
    List<Coltivatore> selectedFromSelected = selectedColtivatoriObsList
        .stream()
        .filter(coltivatore -> selectedSelectionMap.containsKey(coltivatore)
            && selectedSelectionMap.get(coltivatore).get())
        .collect(Collectors.toList());

    // Aggiungi i coltivatori selezionati alla tabella di destinazione
    availableColtivatoriObsList.addAll(selectedFromSelected);

    // Rimuovi i coltivatori selezionati dalla tabella di origine
    selectedColtivatoriObsList.removeAll(selectedFromSelected);

    // Reset delle selezioni dopo il trasferimento
    selectedFromSelected.forEach(coltivatore -> {
      if (selectedSelectionMap.containsKey(coltivatore)) {
        selectedSelectionMap.get(coltivatore).set(false);
      }
    });
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
