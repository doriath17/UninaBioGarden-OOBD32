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

public class ControllerCreaNotifica {

    @FXML
    private TableColumn<?, ?> dataInizioProgettoColonna;

    @FXML
    private TextArea descrizioneField;

    @FXML
    private TableColumn<?, ?> descrizioneProgettoColonna;

    @FXML
    private Label errorLable;

    @FXML
    private VBox mainContent;

    @FXML
    private TextField nomeField;

    @FXML
    private TableColumn<?, ?> nomeProgettoColonna;

    @FXML
    private TableView<?> progettoTable;

    @FXML
    private TableColumn<?, ?> statoProgettoColonna;

    @FXML
    private ChoiceBox<?> urgenzaField;

    @FXML
    void initialize() {
        
    }

    @FXML
    void indietroAction(ActionEvent event) {
        UIController.getInstance().openNotificheView();
    }

    @FXML
    void prossimoStepAction(ActionEvent event) {
        UIController.getInstance().openCreaNotificheStep2View();
    }

}

