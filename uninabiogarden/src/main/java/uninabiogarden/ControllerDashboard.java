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

  @FXML private LineChart<?, ?> reportChart;

  @FXML private TableColumn<Notifica, String> urgenzaNotificaColonna;

  @FXML private TableView<Notifica> notificheTable;

  private final ObservableList<Notifica> notifiche = FXCollections.observableArrayList();


  @FXML
  private void initialize() {
    messaggiobenvenuto.setText("Benvenuto " + MainController.getInstance().getUtenteLoggato().getNome() + "!");

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

        // loadData();

    }

    // //TODO: da rimuovere
    // public void loadData() {
    //     System.out.println("Caricamento notifiche dal database...");

    //     ArrayList<Notifica> dataFromDb = NotificaDAO.getInstance().getAllNotifiche();
    //     notifiche.setAll(dataFromDb); 

    //     // per testing
    //     for (Notifica n : dataFromDb) {
    //         System.out.println("Notifica: " + n.getNome() + ", Progetto: " + n.getProgetto().getNomeProgetto());
    //     }
    // }


}