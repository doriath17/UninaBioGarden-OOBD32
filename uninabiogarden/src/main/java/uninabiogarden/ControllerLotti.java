package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;

public class ControllerLotti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<Lotto> lottiTable;

  @FXML
  private TableColumn<Lotto, String> codiceLottoColumn;

  @FXML
  private TableColumn<Lotto, String> estensioneColumn;

  @FXML
  private TableColumn<Lotto, String> tipologiaTerrenoColumn;

  @FXML
  private TableColumn<Lotto, String> nomeOrtoColumn;

  @FXML
  private TableColumn<Lotto, String> indirizzoColumn;

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
    UIController.getInstance().openCreaLottoView();
  }

}
