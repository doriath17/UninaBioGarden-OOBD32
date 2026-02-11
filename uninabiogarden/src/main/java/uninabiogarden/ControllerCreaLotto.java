package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Orto;

public class ControllerCreaLotto {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField codiceLottoInputField;

  @FXML
  private TextField estensioneLottoInputField;

  @FXML
  private TableView<Orto> availableOrtiTable;

  @FXML
  private TableColumn<Orto, String> nomeOrtoColumn;

  @FXML
  private TableColumn<Orto, String> indirizzoOrtoColumn;

  @FXML
  private Label nomeOrtoSelectedLabel;

  @FXML
  private Label indirizzoOrtoSelectedLabel;

  @FXML
  private Label errorLabel;

  @FXML
  public void initialize() {
    // TODO: initialize table, selection listeners
  }

  @FXML
  public void indietroAction() {
    UIController.getInstance().openLottiView();
  }

  @FXML
  public void confermaAction() {
    // TODO: validate inputs and persist new lotto
  }

}
