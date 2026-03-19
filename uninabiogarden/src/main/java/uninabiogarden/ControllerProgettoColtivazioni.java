package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;

public class ControllerProgettoColtivazioni {

  @FXML
  private TableView<Coltivazione> coltivazioniTable;

  @FXML
  private TableColumn<Coltivazione, String> nomeColturaColumn;

  @FXML
  private TableColumn<Coltivazione, String> statoColumn;

  @FXML
  private TableColumn<Coltivazione, String> statoSaluteColumn;

  @FXML
  private TableColumn<Coltivazione, Integer> quantitaPianteColumn;

  @FXML
  private TableColumn<Coltivazione, Integer> tempoMaturazioneColumn;

  @FXML
  private TableColumn<Coltivazione, Void> viewColumn;

  private ObservableList<Coltivazione> coltivazioniObsList;

  private Progetto progetto;
  private Label errorLabel;

  @FXML
  private void initialize() {
    // Setup table columns con custom cell factories per accedere agli oggetti
    // nested
    nomeColturaColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
        cellData.getValue().getColtura() != null ? cellData.getValue().getColtura().getNomeComune() : "N/A"));

    statoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
        cellData.getValue().getStato() != null ? cellData.getValue().getStato().name() : "N/A"));

    statoSaluteColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
        cellData.getValue().getStatoSalute() != null ? cellData.getValue().getStatoSalute().name() : "N/A"));

    tempoMaturazioneColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
        cellData.getValue().getColtura() != null ? cellData.getValue().getColtura().getTempoMaturazione() : null));

    // Setup actions column with View button
    Utils.addButtonToColumn(viewColumn, "View", this::openDettaglioColtivazione);
  }

  public void init(Progetto progetto, Label errorLabel) {
    this.progetto = progetto;
    this.errorLabel = errorLabel;
    coltivazioniObsList = FXCollections.observableList(progetto.getColtivazioni());
    coltivazioniTable.setItems(coltivazioniObsList);
    coltivazioniTable.refresh();
    errorLabel.setText("");
  }

  private void openDettaglioColtivazione(Coltivazione coltivazione) {
    System.out.println("Apertura dettagli coltivazione: " +
        (coltivazione.getColtura() != null ? coltivazione.getColtura().getNomeComune() : "N/A"));
    UIController.getInstance().openDettaglioColtivazioneView(progetto, coltivazione);
  }

}
