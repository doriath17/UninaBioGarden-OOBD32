package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uninabiogarden.entities.Progetto;

public class ControllerProgettoAttivita {

  @FXML
  private TableView<?> attivitaTable;

  @FXML
  private TableColumn<?, ?> titoloColumn;

  @FXML
  private TableColumn<?, ?> statoColumn;

  @FXML
  private TableColumn<?, ?> pianificazioneColumn;

  @FXML
  private TableColumn<?, ?> scadenzaColumn;

  @FXML
  private TableColumn<?, ?> inizioColumn;

  @FXML
  private TableColumn<?, ?> fineColumn;

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
