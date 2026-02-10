package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControllerOrti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<?> attivitaTable;

  @FXML
  private TableColumn<?, String> nomeOrtoColumn;

  @FXML
  private TableColumn<?, String> indirizzoColumn;

  @FXML
  private TableColumn<?, String> proprietarioColumn;

  @FXML
  public void initialize() {

  }

  @FXML
  public void search() {
    // TODO: implement search logic
  }

  @FXML
  public void indietroAction() {
    UIController.getInstance().openHomeView();
  }

  @FXML
  public void creaOrtoAction() {
    UIController.getInstance().openCreaOrtoView();
  }

}
