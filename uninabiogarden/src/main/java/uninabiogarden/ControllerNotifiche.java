package uninabiogarden;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.NotificaAttivita;
import uninabiogarden.entities.Proprietario;

public class ControllerNotifiche {

  @FXML
  private TableColumn<Notifica, String> attivitaNotificaColonna;

  @FXML
  private TableColumn<Notifica, LocalDate> dataInvioNotificaColonna;

  @FXML
  private TableColumn<Notifica, String> descrizioneNotificaColonna;

  @FXML
  private VBox mainContent;

  @FXML
  private TableColumn<Notifica, String> nomeNotificaColonna;

  @FXML
  private TableView<Notifica> notificheTable;

  @FXML
  private TableColumn<Notifica, String> progettoNotificaColonna;

  @FXML
  private TableColumn<Notifica, String> urgenzaNotificaColonna;

  @FXML
  private TableColumn<Notifica, String> tipoNotificaColonna;

  @FXML
  private TableColumn<Notifica, Integer> giorniMancantiColonna;

  @FXML
  private TableColumn<Notifica, String> mittenteNotificaColonna;

  @FXML
  private Button creaNotificaAttivitaButton;

  @FXML
  private Button creaNotificaGeneraleButton;

  @FXML
  private Button indietroButton;

  private final ObservableList<Notifica> notifiche = FXCollections.observableArrayList();

  @FXML
  void initialize() {

    nomeNotificaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getNome();
      return new SimpleStringProperty(a);
    });

    descrizioneNotificaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getDescrizione();
      return new SimpleStringProperty(a);
    });

    urgenzaNotificaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getUrgenza().toString();
      return new SimpleStringProperty(a);
    });

    dataInvioNotificaColonna.setCellValueFactory(cellData -> {
      LocalDate a = cellData.getValue().getDataInvio();
      return new SimpleObjectProperty<>(a);
    });

    progettoNotificaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getProgetto().getNomeProgetto();
      return new SimpleStringProperty(a.isEmpty() || (a == null) ? "No Data" : a);
    });

    attivitaNotificaColonna.setCellValueFactory(cellData -> {
      Notifica n = cellData.getValue();
      String nomeAtt = "No Data";
      if (n instanceof NotificaAttivita) {
        NotificaAttivita na = (NotificaAttivita) n;
        nomeAtt = na.getAttivita().getNome();
      }
      return new SimpleStringProperty(nomeAtt);
    });

    tipoNotificaColonna.setCellValueFactory(cellData -> {
      Notifica n = cellData.getValue();
      String tipo = (n instanceof NotificaAttivita) ? "NOTIFICA_ATTIVITA_IMMINENTE" : "NOTIFICA_PROGETTO";
      return new SimpleStringProperty(tipo);
    });

    giorniMancantiColonna.setCellValueFactory(cellData -> {
      Notifica n = cellData.getValue();
      Integer giorni = null;
      if (n instanceof NotificaAttivita) {
        NotificaAttivita na = (NotificaAttivita) n;
        giorni = na.getGiorniMancanti();
      }
      return new SimpleObjectProperty<>(giorni);
    });

    mittenteNotificaColonna.setCellValueFactory(cellData -> {
      String a = cellData.getValue().getMittente().getNome();
      return new SimpleStringProperty(a);
    });

    // loadData();
    init();

    notificheTable.setItems(notifiche);
  }

  public void init() {
    var isProprietario = MainController.getInstance().getUtenteLoggato() instanceof Proprietario;

    // disabilita per il coltivatore
    creaNotificaAttivitaButton.setVisible(isProprietario);
    creaNotificaAttivitaButton.setManaged(isProprietario);
    creaNotificaGeneraleButton.setVisible(isProprietario);
    creaNotificaGeneraleButton.setManaged(isProprietario);
    indietroButton.setVisible(isProprietario);
    indietroButton.setManaged(isProprietario);

    notifiche.clear();
    notifiche.addAll(MainController.getInstance().getNotifiche());
    // notificheTable.setItems(notifiche);
    notificheTable.refresh();
  }

  // public void loadData() {
  // notifiche.clear();
  // notifiche.addAll(MainController.getInstance().getNotifiche());
  // }

  @FXML
  void creaNotificaAction(ActionEvent event) {
    UIController.getInstance().openCreaNotificheView(new Notifica(), true);
  }

  @FXML
  void creaNotificaAttivitaAction(ActionEvent event) {
    UIController.getInstance().openCreaNotificheAttivitaView(new Notifica(), true);
  }

  @FXML
  void indietroAction(ActionEvent event) {
    UIController.getInstance().openProprietarioHomeView();
  }

}
