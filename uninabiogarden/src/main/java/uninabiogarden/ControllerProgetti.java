package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
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
  private TextField searchQueryField;

  @FXML
  private Button cercaPerCodiceLotto;

  @FXML
  private Button resetRicerca;

  @FXML
  private TableView<Progetto> progettiTable;

  @FXML
  private TableColumn<Progetto, String> nomeColumn;

  @FXML
  private TableColumn<Progetto, String> statoColumn;

  @FXML
  private TableColumn<Progetto, String> dataInizioColumn;

  @FXML
  private TableColumn<Progetto, String> lottoColumn;

  @FXML
  private TableColumn<Progetto, Void> actionsColumn;

  @FXML
  private TableColumn<Progetto, Void> deleteColumn;

  private ObservableList<Progetto> progettiObservableList;

  @FXML
  private void initialize() {
    // Setup table columns with custom cell value factories to enable live updates
    nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeProgetto()));
    statoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getStato() != null ? cellData.getValue().getStato().name() : ""));
    dataInizioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getDataInizio() != null ? cellData.getValue().getDataInizio().toString() : ""));
    lottoColumn.setCellValueFactory(cellData -> {
      var lotto = cellData.getValue().getLotto();
      return new SimpleStringProperty(lotto != null ? lotto.getFullname() : "");
    });

    Utils.addButtonToColumn(actionsColumn, "View", this::openDettaglioProgetto);
    Utils.addButtonToColumn(deleteColumn, "Elimina", this::deleteProgetto);
  }

  public void init() {
    // Crea ObservableList dal model - sincronizzato con la lista del model
    progettiObservableList = FXCollections.observableList(MainController.getInstance().getProgetti());
    progettiTable.setItems(progettiObservableList);
    progettiTable.refresh(); // Forza refresh per assicurare che i dati siano visualizzati correttamente
  }

  @FXML
  private void search(ActionEvent event) {
    String query = searchQueryField.getText().trim().toLowerCase();
    System.out.println("Ricerca progetti per codice lotto con query: \"" + query + "\"");
    if (query.isEmpty()) {
      System.out.println("Campo di ricerca vuoto, mostra tutti i progetti.");
      return;
    } else {
      System.out.println("Cercando progetti con codice lotto che contiene: " + query);
      ObservableList<Progetto> filtered = FXCollections.observableList(
          MainController.getInstance().getProgetti().stream()
              .filter(p -> p.getLotto() != null && p.getLotto().getFullname() != null &&
                  p.getLotto().getFullname().toLowerCase().contains(query))
              .toList());
      progettiTable.setItems(filtered);
      progettiTable.refresh();
    }
  }

  @FXML
  private void resetSearch(ActionEvent event) {
    searchQueryField.clear();
    progettiTable.setItems(FXCollections.observableList(MainController.getInstance().getProgetti()));
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

  private void deleteProgetto(Progetto progetto) {
    Utils.mostraDialogConfermaConAzione(
        "Sei sicuro di voler eliminare il progetto \"" + progetto.getNomeProgetto() + "\"?",
        progetto,
        p -> {
          try {
            MainController.getInstance().deleteProgetto(p);
            progettiTable.refresh();
          } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
            System.err.println("Errore durante l'eliminazione del progetto: " + e.getMessage());
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Errore durante l'eliminazione del progetto. Riprova più tardi.");
            alert.showAndWait();
          }
        });
  }

}
