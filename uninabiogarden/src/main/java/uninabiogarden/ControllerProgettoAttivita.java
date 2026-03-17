package uninabiogarden;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Concimazione;
import uninabiogarden.entities.Irrigazione;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Raccolta;
import uninabiogarden.entities.Semina;
import uninabiogarden.entities.Trattamento;

public class ControllerProgettoAttivita {

  @FXML
  private TableView<Attivita> attivitaTable;

  @FXML
  private TableColumn<Attivita, String> titoloColumn;

  @FXML
  private TableColumn<Attivita, String> tipologiaColumn;

  @FXML
  private TableColumn<Attivita, String> statoColumn;

  @FXML
  private TableColumn<Attivita, String> nomeColturaColumn;

  @FXML
  private TableColumn<Attivita, Void> viewColumn;

  private Progetto progetto;
  private Label errorLabel;

  @FXML
  private void initialize() {
    titoloColumn.setCellValueFactory(
        cell -> new SimpleStringProperty(cell.getValue().getNome()));

    tipologiaColumn.setCellValueFactory(
        cell -> new SimpleStringProperty(getTipologia(cell.getValue())));

    statoColumn.setCellValueFactory(
        cell -> new SimpleStringProperty(
            cell.getValue().getStato() != null ? cell.getValue().getStato().name() : ""));

    nomeColturaColumn.setCellValueFactory(
        cell -> new SimpleStringProperty(
            cell.getValue().getColtivazione() != null
                && cell.getValue().getColtivazione().getColtura() != null
                    ? cell.getValue().getColtivazione().getColtura().getNomeComune()
                    : ""));

    Utils.addButtonToColumn(viewColumn, "View", this::openDettaglioAttivita);
  }

  public void init(Progetto progetto, Label errorLabel) {
    this.progetto = progetto;
    this.errorLabel = errorLabel;
    refreshAttivita();
    errorLabel.setText("");
  }

  private void refreshAttivita() {
    List<Attivita> allAttivita = new ArrayList<>();
    for (Coltivazione coltivazione : progetto.getColtivazioni()) {
      allAttivita.addAll(coltivazione.getAttivita());
    }
    attivitaTable.setItems(FXCollections.observableArrayList(allAttivita));
  }

  private void openDettaglioAttivita(Attivita attivita) {
    UIController.getInstance().openDettaglioAttivitaView(progetto, attivita.getColtivazione(), attivita);
  }

  private String getTipologia(Attivita a) {
    if (a instanceof Semina)
      return "SEMINA";
    if (a instanceof Irrigazione)
      return "IRRIGAZIONE";
    if (a instanceof Concimazione)
      return "CONCIMAZIONE";
    if (a instanceof Trattamento)
      return "TRATTAMENTO";
    if (a instanceof Raccolta)
      return "RACCOLTA";
    return "N/A";
  }

}
