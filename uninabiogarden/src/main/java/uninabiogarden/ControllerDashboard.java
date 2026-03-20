package uninabiogarden;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import uninabiogarden.dao.NotificaDAO;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.Progetto;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleObjectProperty;

public class ControllerDashboard {


  @FXML private TableView<Progetto> ProgettiTable;

  @FXML private TableColumn<Notifica, LocalDate> dataInvioNotificaColonna;

  @FXML private TableColumn<Notifica, String> descrizioneNotificaColonna;

  @FXML private VBox mainContent;

  @FXML private Label messaggiobenvenuto;

  @FXML private TableColumn<Notifica, String> nomeNotificaColonna;

  @FXML private TableColumn<Notifica, String> progettoNotificaColonna;

  @FXML private TableColumn<Notifica, String> urgenzaNotificaColonna;

  @FXML private TableView<Notifica> notificheTable;

  @FXML private TableColumn<Progetto, String> coltivazioneProgetto;

  @FXML private TableColumn<Progetto, String> descrizioneProgetto;

  @FXML private TableColumn<Progetto, String> lottoProgetto;

  @FXML private TableColumn<Progetto, String> nomeProgetto;

  @FXML private TableColumn<Progetto, String> statoProgetto;

  private final ObservableList<Notifica> notifiche = FXCollections.observableArrayList();
  private final ObservableList<Progetto> progetti = FXCollections.observableArrayList();


  @FXML
  private void initialize() {

    messaggiobenvenuto.setText("Benvenuto " + MainController.getInstance().getUtenteLoggato().getNome() + "!");


    // Tabella notifiche
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
  
    progettoNotificaColonna.setCellValueFactory(cellData -> {
        String a = cellData.getValue().getProgetto().getNomeProgetto();
        return new SimpleStringProperty(a);
    });

    dataInvioNotificaColonna.setCellValueFactory(cellData -> {
        LocalDate timestamp = cellData.getValue().getDataInvio();
        return new SimpleObjectProperty<>(timestamp);
    });

    // Tabella progetti
    nomeProgetto.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getNomeProgetto()));

    statoProgetto.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getStato() != null ? cellData.getValue().getStato().name() : ""));

    lottoProgetto.setCellValueFactory(cellData -> {
        var lotto = cellData.getValue().getLotto();
        return new SimpleStringProperty(lotto != null ? lotto.getFullname() : "");
    });

    descrizioneProgetto.setCellValueFactory(cellData -> 
    new SimpleStringProperty(cellData.getValue().getDescrizione() != null ? cellData.getValue().getDescrizione() : ""));

    coltivazioneProgetto.setCellValueFactory(cellData -> {
        var coltivazioni = cellData.getValue().getColtivazioni();
        String count = (coltivazioni != null) ? String.valueOf(coltivazioni.size()) : "0";
        return new SimpleStringProperty(count);
    });

    loadData();

    notificheTable.setItems(notifiche);
    ProgettiTable.setItems(progetti);

    }

    public void loadData() {
        notifiche.clear();
        notifiche.addAll(MainController.getInstance().getNotifiche());

        progetti.clear();
        progetti.addAll(MainController.getInstance().getProgetti());
    }

    public void init() {
        notifiche.clear();
        notifiche.addAll(MainController.getInstance().getNotifiche());
        
        progetti.clear();
        progetti.addAll(MainController.getInstance().getProgetti());
        
        notificheTable.refresh();
        ProgettiTable.refresh();
    }

}