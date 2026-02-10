package uninabiogarden;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Orto;

public class ControllerOrti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<Orto> ortiTable;

  @FXML
  private TableColumn<Orto, String> nomeOrtoColumn;

  @FXML
  private TableColumn<Orto, String> indirizzoColumn;

  @FXML
  private TableColumn<Orto, String> proprietarioColumn;

  @FXML
  public void initialize() {
    nomeOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeOrto"));
    indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
    proprietarioColumn.setCellValueFactory(new PropertyValueFactory<>("proprietarioFullName"));

    ortiTable.setItems(MainController.getInstance().getOrtiObservableList());
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
