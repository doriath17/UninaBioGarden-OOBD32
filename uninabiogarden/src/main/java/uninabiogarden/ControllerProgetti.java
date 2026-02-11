package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;

public class ControllerProgetti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<?> progettiTable;

  @FXML
  private TableColumn<?, ?> nomeColumn;

  @FXML
  private TableColumn<?, ?> statoColumn;

  @FXML
  private TableColumn<?, ?> dataCreazioneColumn;

  @FXML
  private TableColumn<?, ?> dataInizioColumn;

  @FXML
  private TableColumn<?, ?> dataFineColumn;

  @FXML
  private TableColumn<?, ?> actionsColumn;

  @FXML
  private void initialize() {
    // initialization if needed
  }

  @FXML
  private void search(ActionEvent event) {
    // TODO: implement search logic
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openProprietarioHomeView();
  }

  @FXML
  private void creaProgettoAction(ActionEvent event) {
    UIController.getInstance().openCreaProgettoStep1View();
  }

}
