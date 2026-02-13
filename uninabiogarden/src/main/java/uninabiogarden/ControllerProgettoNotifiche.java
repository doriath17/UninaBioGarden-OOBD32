package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uninabiogarden.entities.Progetto;

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

  private Progetto progetto;
  private Label errorLabel;

  @FXML
  private void initialize() {
    // Setup table columns
  }

  public void init(Progetto progetto, Label errorLabel) {
    this.progetto = progetto;
    this.errorLabel = errorLabel;
  }

}
