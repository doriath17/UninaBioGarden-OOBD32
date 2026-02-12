package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import uninabiogarden.entities.Progetto;

public class ControllerProgetti {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField searchField;

  @FXML
  private TableView<Progetto> progettiTable;

  @FXML
  private TableColumn<Progetto, String> nomeColumn;

  @FXML
  private TableColumn<Progetto, String> statoColumn;

  @FXML
  private TableColumn<Progetto, String> dataCreazioneColumn;

  @FXML
  private TableColumn<Progetto, String> dataInizioColumn;

  @FXML
  private TableColumn<Progetto, String> dataFineColumn;

  @FXML
  private TableColumn<Progetto, Void> actionsColumn;

  private ObservableList<Progetto> progettiObservableList;

  @FXML
  private void initialize() {
    // Setup table columns
    nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nomeProgetto"));
    statoColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
    dataCreazioneColumn.setCellValueFactory(new PropertyValueFactory<>("dataCreazione"));
    dataInizioColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
    dataFineColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

    // Setup actions column with View button
    Utils.addButtonToColumn(actionsColumn, "View", this::openDettaglioProgetto);
  }

  public void init() {
    // Crea ObservableList dal model - sincronizzato con la lista del model
    progettiObservableList = FXCollections.observableList(MainController.getInstance().getProgetti());
    progettiTable.setItems(progettiObservableList);
  }

  @FXML
  private void search(ActionEvent event) {
    // TODO: da rimuovere probabilmente
  }

  @FXML
  private void indietroAction(ActionEvent event) {
    UIController.getInstance().openProprietarioHomeView();
  }

  @FXML
  private void creaProgettoAction(ActionEvent event) {
    UIController.getInstance().openCreaProgettoStep1View(null, true);
  }

  private void openDettaglioProgetto(Progetto progetto) {
    UIController.getInstance().openDettaglioProgettoView(progetto);
  }

}
