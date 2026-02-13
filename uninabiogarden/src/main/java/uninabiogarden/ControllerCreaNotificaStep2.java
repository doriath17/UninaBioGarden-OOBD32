package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ControllerCreaNotificaStep2 {

    @FXML
    private TableView<?> availableColtivatoriTable;

    @FXML
    private TableColumn<?, ?> availableNomeColumn;

    @FXML
    private TableColumn<?, ?> availableSelectionColumn;

    @FXML
    private TableColumn<?, ?> availableUsernameColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox mainContent;

    @FXML
    private TableView<?> selectedColtivatoriTable;

    @FXML
    private TableColumn<?, ?> selectedNomeColumn;

    @FXML
    private TableColumn<?, ?> selectedSelectionColumn;

    @FXML
    private TableColumn<?, ?> selectedUsernameColumn;

    @FXML
    private ChoiceBox<?> yesNoBox;

    @FXML
    void deselezionaSelezionati(ActionEvent event) {

    }

    @FXML
    void indietroAction(ActionEvent event) {
        UIController.getInstance().openCreaNotificheView();
    }

    @FXML
    void nextStepAction(ActionEvent event) {

    }

    @FXML
    void selezionaDisponibili(ActionEvent event) {

    }

}
