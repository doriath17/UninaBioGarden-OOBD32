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
import uninabiogarden.entities.NotificaAttivita;

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

  private boolean vengoDaAttivita = false;

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

  public void init(Notifica notifica) {

    // if (notifica instanceof NotificaAttivita) {
    // this.nuovaNotifica = (NotificaAttivita) notifica;
    // this.vengoDaAttivita = true;
    // } else {
    // this.nuovaNotifica = new NotificaAttivita();
    // }

    // per capire se vengo da crea notifica attivita o da crea notifica progetto
    this.nuovaNotifica = notifica;
    this.vengoDaAttivita = (notifica instanceof NotificaAttivita);

    clear();
    loadColtivatoriDisponibili();
    aggiornaUIperNotificaAttivita();
  }

  // aggiorna l'interfaccia in base al tipo di notifica (attività o progetto)

  private void aggiornaUIperNotificaAttivita() {
    if (vengoDaAttivita) {

      yesNoBox.setDisable(true);
      yesNoBox.setValue("No");

      // errorLabel.setText("Verrà selezionato solo il coltivatore in cima alla lista
      // siccome la notifica imminente");

    } else {

      yesNoBox.setDisable(false);
      errorLabel.setText("");

    }
  }

  private void loadColtivatoriDisponibili() {

    availableSelectionMap.clear();
    selectedSelectionMap.clear();
    availableColtivatoriObsList.clear();
    selectedColtivatoriObsList.clear();

    List<Coltivatore> coltivatoriDisponibili = new ArrayList<>();

    if (nuovaNotifica != null && nuovaNotifica.getProgetto() != null) {
      coltivatoriDisponibili = nuovaNotifica.getProgetto().getColtivatori();
    }
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

    if (vengoDaAttivita) {
      UIController.getInstance().openCreaNotificheAttivitaView(nuovaNotifica, false);
    } else {
      UIController.getInstance().openCreaNotificheView(nuovaNotifica, false);
    }

  }

  @FXML
  private void nextStepAction(ActionEvent event) {

    if (nuovaNotifica.getProgetto() == null) {
      errorLabel.setText("Progetto non trovato");
      return;
    }

    if (vengoDaAttivita) {

      // caso notifica attivita
      if (selectedColtivatoriObsList.size() > 1) {
        errorLabel.setText("Puoi selezionare solo UN coltivatore per la notifica attività");
        return;
      }
      if (selectedColtivatoriObsList.isEmpty()) {
        errorLabel.setText("Seleziona un coltivatore per la notifica attività");
        return;
      }

      nuovaNotifica.setDestinatari(new ArrayList<>(selectedColtivatoriObsList));

    } else {

      // caso notifica generale progetto
      String yesNoValue = yesNoBox.getValue();
      if (yesNoValue == null || yesNoValue.toString().isEmpty()) {
        errorLabel.setText("Seleziona un'opzione per continuare");
        return;
      }

      if (yesNoValue.toString().equals("Si")) {

        // caso mandare notifica a tutti i coltivatori del progetto
        List<Coltivatore> coltivatoriProgetto = nuovaNotifica.getProgetto().getColtivatori();

        if (coltivatoriProgetto != null && !coltivatoriProgetto.isEmpty()) {
          nuovaNotifica.setDestinatari(new ArrayList<>(coltivatoriProgetto));

        } else {

          errorLabel.setText("Nessun coltivatore associato al progetto");
          return;
        }

      } else {

        // caso mandare notifica solo a coltivatori selezionati
        ArrayList<Coltivatore> coltivatoriSelezionati = new ArrayList<>(selectedColtivatoriObsList);
        if (coltivatoriSelezionati.isEmpty()) {
          errorLabel.setText("Seleziona almeno un destinatario per procedere");
          return;
        }
        nuovaNotifica.setDestinatari(coltivatoriSelezionati);

      }
    }

    // per testing
    System.out.println("Notifica da inviare: " + nuovaNotifica.toString());

    Utils.mostraDialogConfermaConAzione(
        "Vuoi davvero creare la notifica?",
        nuovaNotifica,
        (lambda) -> {
          // Salva la notifica nel database
          MainController.getInstance().getDatabaseController().getNotificaDao().saveNotifica(nuovaNotifica);

          // DatabaseController.getInstance().getNotificaDao().saveNotifica(nuovaNotifica);
          MainController.getInstance().getNotifiche().add(nuovaNotifica);
          System.out.println("Notifica salvata con successo!");

          UIController.getInstance().openNotificheView();
        });

    clear();
  }
}