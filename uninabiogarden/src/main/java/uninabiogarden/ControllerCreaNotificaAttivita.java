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
import uninabiogarden.entities.Attivita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class ControllerCreaNotificaAttivita {

  @FXML
  private TableView<Attivita> attivitaTable;

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
  private TableColumn<Attivita, String> noteTecnicheAttivitaColonna;

  @FXML
  private TableView<Progetto> progettoTable;

  @FXML
  private TableColumn<Attivita, String> statoAttivitaColonna;

  @FXML
  private TableColumn<Progetto, String> statoProgettoColonna;

  @FXML
  private TableColumn<Attivita, String> titoloAttivitaColonna;

  @FXML
  private ChoiceBox<Notifica.Urgenza> urgenzaField;

  ObservableList<Attivita> attivita = javafx.collections.FXCollections.observableArrayList();
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

    // tabella attivita
    titoloAttivitaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getNome();
      return new SimpleStringProperty(a);
    });

    noteTecnicheAttivitaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getNoteTecniche();
      return new SimpleStringProperty(a);
    });

    statoAttivitaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getStato().toString();
      return new SimpleStringProperty(a);
    });

    progettoTable.setItems(progetti);

  }

  public void init(Notifica nuovaNotifica) {
    this.nuovaNotifica = nuovaNotifica != null ? nuovaNotifica : new Notifica();
    clear();
    progettoTable.getSelectionModel().clearSelection();
    attivitaTable.getSelectionModel().clearSelection();
    progetti.setAll(MainController.getInstance().getProgetti());
    progettoTable.refresh();

    // TODO: da implementare quando attività sarà implementata
    // attivita.setAll(MainController.getInstance().getAttivita());
    // attivitaTable.setItems(attivita);
    // attivitaTable.refresh();
  }

  @FXML
  void indietroAction(ActionEvent event) {
    clear();
    UIController.getInstance().openNotificheView();
  }

  @FXML
  void prossimoStepAction(ActionEvent event) {

    if (isFormInvalid()) {
      return;
    }

    getData(nuovaNotifica);

    UIController.getInstance().openCreaNotificaStep2View(nuovaNotifica, false);

    // for testign
    System.out.println(nuovaNotifica.toString());
  }

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
    if (attivitaTable.getSelectionModel().getSelectedItem() == null) {
      errorLable.setText("Seleziona un'attivita dalla tabella");
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
    notifica.setTipo(Notifica.Tipo.NOTIFICA_ATTIVITA_IMMINENTE);
    notifica.setMittente((Proprietario) MainController.getInstance().getUtenteLoggato());

    Progetto prg = progettoTable.getSelectionModel().getSelectedItem();
    if (prg != null) {
      notifica.setProgetto(prg);
    }
  }

}