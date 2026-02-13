package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ControllerNotifiche {

    @FXML
    private TableColumn<?, ?> attivitaNotificaColonna;

    @FXML
    private TableColumn<?, ?> dataInvioNotificaColonna;

    @FXML
    private TableColumn<?, ?> descrizioneNotificaColonna;

    @FXML
    private VBox mainContent;

    @FXML
    private TableColumn<?, ?> nomeNotificaColonna;

    @FXML
    private TableView<?> notificheTable;

    @FXML
    private TableColumn<?, ?> progettoNotificaColonna;

    @FXML
    private TableColumn<?, ?> urgenzaNotificaColonna;

    @FXML
    void inizialize() {
       
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
