package uninabiogarden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.dao.DatabaseController;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Notifica;

public class ControllerCreaNotificaStep2 {

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

  @FXML
  private VBox mainContent;

  @FXML
  private ChoiceBox<String> yesNoBox;

  private ObservableList<Coltivatore> availableColtivatoriObsList = FXCollections.observableArrayList();
  private ObservableList<Coltivatore> selectedColtivatoriObsList = FXCollections.observableArrayList();

  private Map<Coltivatore, SimpleBooleanProperty> availableSelectionMap = new HashMap<>();
  private Map<Coltivatore, SimpleBooleanProperty> selectedSelectionMap = new HashMap<>();

  private Notifica nuovaNotifica;

  @FXML
  private void initialize() {
    clear();

    yesNoBox.getItems().addAll("Si", "No");
    yesNoBox.setValue("No");

    availableNomeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    Utils.<Coltivatore>setupCheckBoxColumn(availableSelectionColumn, availableSelectionMap);

    availableColtivatoriTable.setItems(availableColtivatoriObsList);

    selectedNomeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    selectedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    Utils.<Coltivatore>setupCheckBoxColumn(selectedSelectionColumn, selectedSelectionMap);

    selectedColtivatoriTable.setItems(selectedColtivatoriObsList);

  }

  public void init(Notifica nuovaNotifica) {
    this.nuovaNotifica = nuovaNotifica != null ? nuovaNotifica : new Notifica();
    clear();
    loadColtivatoriDisponibili();
  }

  private void loadColtivatoriDisponibili() {
    List<Coltivatore> coltivatoriDisponibili = MainController.getInstance().getColtivatori();
    availableColtivatoriObsList.setAll(coltivatoriDisponibili);
  }

  @FXML
  private void selezionaDisponibili(ActionEvent event) {
    Utils.<Coltivatore>moveSelectionTo(availableColtivatoriObsList, selectedColtivatoriObsList,
        availableSelectionMap, selectedSelectionMap);
  }

  @FXML
  private void deselezionaSelezionati(ActionEvent event) {
    Utils.<Coltivatore>moveSelectionTo(selectedColtivatoriObsList, availableColtivatoriObsList,
        selectedSelectionMap, availableSelectionMap);
  }

  private void clear() {
    errorLabel.setText("");
    availableColtivatoriObsList.clear();
    selectedColtivatoriObsList.clear();
    availableSelectionMap.clear();
    selectedSelectionMap.clear();
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openCreaNotificheView(nuovaNotifica, false);
  }

  @FXML
  private void nextStepAction(ActionEvent event) {

    if (nuovaNotifica.getProgetto() == null) {
      errorLabel.setText("Progetto non trovato");
      return;
    }

    String yesNoValue = yesNoBox.getValue();
    if (yesNoValue == null || yesNoValue.toString().isEmpty()) {
      errorLabel.setText("Seleziona un'opzione per continuare");
      return;
    }

    if (yesNoValue.toString().equals("Si")) {
      // mdanda la notifica a tutti i coltivatori del progetto
      nuovaNotifica.setDestinatari(new ArrayList<>(nuovaNotifica.getProgetto().getColtivatori()));

    } else {

      // manda la notifica solo ai coltivatori selezionati
      ArrayList<Coltivatore> selectedColtivatori = new ArrayList<>(selectedColtivatoriObsList);
      if (selectedColtivatori.isEmpty()) {
        errorLabel.setText("Seleziona almeno un destinatario per procedere");
        return;
      }
      nuovaNotifica.setDestinatari(new ArrayList<>(selectedColtivatori));
    }

    // per testing
    System.out.println("Notifica da inviare: " + nuovaNotifica.toString());

    // non mi piace molto questo metodo così specifico
    Utils.mostraDialogConfermaConAzione(
        "Vuoi davvero creare la notifica?",
        nuovaNotifica,
        (lambda) -> {
          // Salva la notifica nel database
          DatabaseController.getInstance().getNotificaDao().saveNotifica(nuovaNotifica);
          MainController.getInstance().getNotifiche().add(nuovaNotifica);
          System.out.println("Notifica salvata con successo!");
        });

    clear();

  }
}