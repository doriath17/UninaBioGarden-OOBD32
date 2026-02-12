package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerProgettoNotifiche {

  @FXML
  private TableView<?> notificheTable;

  @FXML
  private TableColumn<?, ?> eventoColumn;

  @FXML
  private TableColumn<?, ?> invioColumn;

  @FXML
  private TableColumn<?, ?> urgenzaColumn;

  @FXML
  private TableColumn<?, ?> nomeProgettoColumn;

  @FXML
  private TableColumn<?, ?> giorniMancantiColumn;

  @FXML
  private TableColumn<?, ?> viewColumn;

  @FXML
  private void initialize() {
    // Setup table columns
  }

}
