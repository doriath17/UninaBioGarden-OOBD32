package uninabiogarden;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Progetto;

public class ControllerProgettoColtivatori {

  @FXML
  private TableView<Coltivatore> coltivatoriProgettoTable;

  @FXML
  private TableColumn<Coltivatore, String> usernameColumn;

  @FXML
  private TableColumn<Coltivatore, String> nomeColumn;

  @FXML
  private TableColumn<Coltivatore, String> emailColumn;

  @FXML
  private TableColumn<Coltivatore, Integer> etaColumn;

  private ObservableList<Coltivatore> coltivatoriObsList;

  private Progetto progetto;
  private Label errorLabel;

  @FXML
  private void initialize() {
    // Setup table columns
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    etaColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
  }

  public void init(Progetto progetto, Label errorLabel) {
    this.progetto = progetto;
    this.errorLabel = errorLabel;
    coltivatoriObsList = FXCollections.observableArrayList(progetto.getColtivatori());
    coltivatoriProgettoTable.setItems(coltivatoriObsList);
    errorLabel.setText("");
  }

}
