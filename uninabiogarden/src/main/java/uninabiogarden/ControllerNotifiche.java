package uninabiogarden;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Notifica;

public class ControllerNotifiche {

    @FXML private TableColumn<Notifica, String> attivitaNotificaColonna;

    @FXML private TableColumn<Notifica, LocalDate> dataInvioNotificaColonna;

    @FXML private TableColumn<Notifica, String> descrizioneNotificaColonna;

    @FXML private VBox mainContent;

    @FXML private TableColumn<Notifica, String> nomeNotificaColonna;

    @FXML private TableView<Notifica> notificheTable;

    @FXML private TableColumn<Notifica, String> progettoNotificaColonna;

    @FXML private TableColumn<Notifica, String> urgenzaNotificaColonna;

    @FXML private TableColumn<Notifica, String> tipoNotificaColonna;

    @FXML private TableColumn<Notifica, Integer> giorniMancantiColonna;

    @FXML private TableColumn<Notifica, String> mittenteNotificaColonna;



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
        
        progettoNotificaColonna.setCellValueFactory(cellData -> {
            String a = cellData.getValue().getProgetto().getNomeProgetto();
            return new SimpleStringProperty(a.isEmpty() || (a==null) ? "No Data" : a);
        });

        dataInvioNotificaColonna.setCellValueFactory(cellData -> {
            LocalDate timestamp = cellData.getValue().getDataInvio();
            return new SimpleObjectProperty<>(timestamp);
        });

        attivitaNotificaColonna.setCellValueFactory(cellData -> {
            String a = cellData.getValue().getAttivita() != null ? cellData.getValue().getAttivita().getNome() : "No Data";
            return new SimpleStringProperty(a);
        });

        tipoNotificaColonna.setCellValueFactory(cellData -> {
            String a = cellData.getValue().getTipo().toString();
            return new SimpleStringProperty(a);
        });

        giorniMancantiColonna.setCellValueFactory(cellData -> {
            Integer a = cellData.getValue().getGiorniMancanti();
            return new SimpleObjectProperty<>(a);
        });

        mittenteNotificaColonna.setCellValueFactory(cellData -> {
            String a = cellData.getValue().getMittente().getNome();
            return new SimpleStringProperty(a);
        });

        // loadData(); 
        init();

        notificheTable.setItems(notifiche);
    }

    public void init(){
        notifiche.clear();
        notifiche.addAll(MainController.getInstance().getNotifiche());
        // notificheTable.setItems(notifiche);
        notificheTable.refresh();
    }

    // public void loadData() {
    //     notifiche.clear();
    //     notifiche.addAll(MainController.getInstance().getNotifiche());
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
