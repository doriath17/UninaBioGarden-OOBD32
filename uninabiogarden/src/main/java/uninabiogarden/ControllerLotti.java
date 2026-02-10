package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControllerLotti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<?> ortiTable;

  @FXML
  private TableColumn<?, String> codiceLottoColumn;

  @FXML
  private TableColumn<?, String> estensioneColumn;

  @FXML
  private TableColumn<?, String> tipologiaTerrenoColumn;

  @FXML
  private TableColumn<?, String> nomeOrtoColumn;

  @FXML
  private TableColumn<?, String> indirizzoColumn;

  @FXML
  public void initialize() {
    // TODO: initialize table columns and load data
  }

  @FXML
  public void search() {
    // TODO: filter table items based on searchField text
  }

  @FXML
  public void indietroAction() {
    UIController.getInstance().openHomeView();
  }

  @FXML
  public void creaLottoAction() {
    // TODO: open create-lotto view
  }

}
