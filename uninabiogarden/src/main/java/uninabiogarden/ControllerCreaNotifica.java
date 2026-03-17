package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleStringProperty;

public class ControllerCreaNotifica {

  @FXML
  private TableColumn<Progetto, String> dataInizioProgettoColonna;

  @FXML
  private TextArea descrizioneField;

  @FXML
  private TableColumn<Progetto, String> descrizioneProgettoColonna;

  @FXML
  private Label errorLable;

  @FXML
  private VBox mainContent;

  @FXML
  private TextField nomeField;

  @FXML
  private TableColumn<Progetto, String> nomeProgettoColonna;

  @FXML
  private TableView<Progetto> progettoTable;

  @FXML
  private TableColumn<Progetto, String> statoProgettoColonna;

  @FXML
  private ChoiceBox<Notifica.Urgenza> urgenzaField;

  ObservableList<Progetto> progetti = javafx.collections.FXCollections.observableArrayList();
  Notifica nuovaNotifica;

  @FXML
  void initialize() {
    Utils.addCharacterLimit(nomeField, 100);
    Utils.addCharacterLimit(descrizioneField, 500);

    urgenzaField.getItems().addAll(Notifica.Urgenza.values());
    urgenzaField.setValue(Notifica.Urgenza.BASSA);

    errorLable.setText("");

    // tabella progetti
    dataInizioProgettoColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getDataInizio().toString();
      return new SimpleStringProperty(a);
    });

    descrizioneProgettoColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getDescrizione();
      return new SimpleStringProperty(a);
    });

    nomeProgettoColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getNomeProgetto();
      return new SimpleStringProperty(a);
    });

    statoProgettoColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getStato().toString();
      return new SimpleStringProperty(a);
    });

    // loadData();

    progettoTable.setItems(progetti);

  }

  public void init(Notifica nuovaNotifica) {
    this.nuovaNotifica = nuovaNotifica != null ? nuovaNotifica : new Notifica();
    clear();
    progettoTable.getSelectionModel().clearSelection();
    progetti.setAll(MainController.getInstance().getProgetti());
    progettoTable.refresh();
  }

  // void loadData() {
  // progetti.setAll(MainController.getInstance().getProgetti());
  // }

  boolean isFormInvalid() {
    if (nomeField.getText().trim().isEmpty()) {
      errorLable.setText("Il campo nome è obbligatorio");
      return true;
    }
    if (descrizioneField.getText().trim().isEmpty()) {
      errorLable.setText("Il campo descrizione è obbligatorio");
      return true;
    }
    if (progettoTable.getSelectionModel().getSelectedItem() == null) {
      errorLable.setText("Seleziona un progetto dalla tabella");
      return true;
    }
    return false;
  }

  void clear() {
    nomeField.clear();
    descrizioneField.clear();
    urgenzaField.setValue(Notifica.Urgenza.BASSA);
    errorLable.setText("");
  }

  void getData(Notifica notifica) {
    if (notifica == null) {
      notifica = new Notifica();
    }
    notifica.setNome(nomeField.getText());
    notifica.setDescrizione(descrizioneField.getText());
    notifica.setUrgenza((Notifica.Urgenza) urgenzaField.getValue());
    notifica.setTipo(Notifica.Tipo.NOTIFICA_PROGETTO);
    notifica.setMittente((Proprietario) MainController.getInstance().getUtenteLoggato());

    Progetto prg = progettoTable.getSelectionModel().getSelectedItem();
    if (prg != null) {
      notifica.setProgetto(prg);
    }
  }

  @FXML
  void indietroAction(ActionEvent event) {
    UIController.getInstance().openNotificheView(true);
  }

  @FXML
  void prossimoStepAction(ActionEvent event) {

    if (isFormInvalid()) {
      return;
    }

    getData(nuovaNotifica);

    UIController.getInstance().openCreaNotificaStep2View(nuovaNotifica, true);

    // for testign
    System.out.println(nuovaNotifica.toString());
  }

}