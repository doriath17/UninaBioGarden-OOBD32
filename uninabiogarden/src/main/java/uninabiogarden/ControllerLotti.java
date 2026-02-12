package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

  private ObservableList<Lotto> lottiObservableList;

  @FXML
  public void initialize() {
    codiceLottoColumn.setCellValueFactory(new PropertyValueFactory<>("codiceLotto"));
    estensioneColumn.setCellValueFactory(new PropertyValueFactory<>("estensioneMq"));
    tipologiaTerrenoColumn.setCellValueFactory(new PropertyValueFactory<>("tipologiaTerreno"));
    nomeOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeOrto"));
    indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
  }

  public void init() {
    // Crea ObservableList dal model - sincronizzato con la lista del Proprietario
    lottiObservableList = FXCollections.observableList(MainController.getInstance().getLotti());
    lottiTable.setItems(lottiObservableList);
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
