package uninabiogarden;

import java.util.ArrayList;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.dao.NotificaDAO;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.Progetto;

public class ControllerNotifiche {

    @FXML
    private TableColumn<Notifica, String> attivitaNotificaColonna;

    @FXML
    private TableColumn<Notifica, java.time.LocalDate> dataInvioNotificaColonna;

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
    private TableColumn<Notifica.Urgenza, String> urgenzaNotificaColonna;

    @FXML
    void initialize() {

        nomeNotificaColonna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        descrizioneNotificaColonna.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        // Urgenza 
        urgenzaNotificaColonna.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().toString()));

        // Data Invio 
        dataInvioNotificaColonna.setCellValueFactory(cellData -> {
            var timestamp = cellData.getValue().getDataInvio();
            return new SimpleObjectProperty<>(timestamp);
        });

        // Progetto 
        progettoNotificaColonna.setCellValueFactory(cellData -> {
            var progetto = cellData.getValue().getProgetto();
            return new SimpleStringProperty(progetto != null ? progetto.getNomeProgetto() : "N/A");
        });

        // Attivita
        attivitaNotificaColonna.setCellValueFactory(cellData -> {
            var attivita = cellData.getValue().getAttivita();
            return new SimpleStringProperty(attivita != null ? attivita.getNome() : "-");
        });

        updateTable();
    }

    private void updateTable() {

        NotificaDAO dao = NotificaDAO.getInstance();
        ArrayList<Notifica> data = dao.getAllNotifiche();
        notificheTable.setItems(FXCollections.observableArrayList(data));

    }

    @FXML
    void creaNotificaAction(ActionEvent event) {
        UIController.getInstance().openCreaNotificheView();
    }

    @FXML
    void creaNotificaAttivitaAction(ActionEvent event) {
        UIController.getInstance().openCreaNotificheAttivitaView();
    }

    @FXML
    void indietroAction(ActionEvent event) {
        UIController.getInstance().openProprietarioHomeView();
    }

}
