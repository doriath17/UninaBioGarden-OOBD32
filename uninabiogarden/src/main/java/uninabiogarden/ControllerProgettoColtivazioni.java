package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerProgettoColtivazioni {

  @FXML
  private TableView<?> coltivazioniTable;

  @FXML
  private TableColumn<?, ?> nomeColturaColumn;

  @FXML
  private TableColumn<?, ?> statoColumn;

  @FXML
  private TableColumn<?, ?> statoSaluteColumn;

  @FXML
  private TableColumn<?, ?> quantitaPianteColumn;

  @FXML
  private TableColumn<?, ?> tempoMaturazioneColumn;

  @FXML
  private TableColumn<?, ?> viewColumn;

  @FXML
  private void initialize() {
    // Setup table columns
  }

}
