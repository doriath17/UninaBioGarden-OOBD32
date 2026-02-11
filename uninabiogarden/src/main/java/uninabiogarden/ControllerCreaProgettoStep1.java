package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Lotto;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ControllerCreaProgettoStep1 {

  @FXML
  private VBox mainContent;

  @FXML
  private TableView<Lotto> availableOrtiTable;

  @FXML
  private TableColumn<Lotto, String> codiceLottoColumn;

  @FXML
  private TableColumn<Lotto, String> nomeOrtoColumn;

  @FXML
  private TableColumn<Lotto, String> indirizzoOrtoColumn;

  @FXML
  private TextField nomeProgettoField;

  @FXML
  private TextArea descrizioneField;

  @FXML
  private Label codiceSelectedLotto;

  @FXML
  private Label nomeOrtoSelectedLotto;

  @FXML
  private Label indirizzoSelectedLotto;

  @FXML
  private Label errorLabel;

  private ObservableList<Lotto> lottiDisponibiliObsList = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    Utils.addCharacterLimit(nomeProgettoField, 100);
    Utils.addCharacterLimit(descrizioneField, 500);

    // setup della tabella dei lotti disponibili
    codiceLottoColumn.setCellValueFactory(new PropertyValueFactory<>("codiceLotto"));

    nomeOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeOrto"));

    indirizzoOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));

    availableOrtiTable.setItems(lottiDisponibiliObsList);

    // gestione selezione lotto
    availableOrtiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        codiceSelectedLotto.setText(newSelection.getCodiceLotto());
        nomeOrtoSelectedLotto.setText(newSelection.getNomeOrto());
        indirizzoSelectedLotto.setText(newSelection.getIndirizzo());
      } else {
        codiceSelectedLotto.setText("N/A");
        nomeOrtoSelectedLotto.setText("N/A");
        indirizzoSelectedLotto.setText("N/A");
      }
    });

  }

  public void init() {
    errorLabel.setText("");
    nomeProgettoField.setText("");
    descrizioneField.setText("");
    codiceSelectedLotto.setText("N/A");
    nomeOrtoSelectedLotto.setText("N/A");
    indirizzoSelectedLotto.setText("N/A");

    // carica i lotti disponibili per il coltivatore loggato
    try {
      lottiDisponibiliObsList.setAll(MainController.getInstance().getLottiDisponibili());
      if (lottiDisponibiliObsList.isEmpty()) {
        errorLabel.setText("Non ci sono lotti disponibili al momento");
      }
    } catch (Exception e) {
      System.err.println("Errore durante il caricamento dei lotti disponibili: " + e.getMessage());
      errorLabel.setText("Errore durante il caricamento dei lotti disponibili. Riprova più tardi.");
    }
  }

  private void clear() {
    nomeProgettoField.setText("");
    descrizioneField.setText("");
    availableOrtiTable.getSelectionModel().clearSelection();
    codiceSelectedLotto.setText("N/A");
    nomeOrtoSelectedLotto.setText("N/A");
    indirizzoSelectedLotto.setText("N/A");
    errorLabel.setText("");
  }

  @FXML
  private void indietroAction() {
    clear();
    UIController.getInstance().openProgettiView();
  }

  private ProgettoDto getData() {
    ProgettoDto dto = new ProgettoDto();
    dto.nome = nomeProgettoField.getText();
    dto.descrizione = descrizioneField.getText();
    dto.stato = "PIANIFICATO"; // Stato iniziale del progetto
    // le date sono impostate direttamente nel database
    dto.lottoId = availableOrtiTable.getSelectionModel().getSelectedItem() != null
        ? availableOrtiTable.getSelectionModel().getSelectedItem().getId()
        : null;
    return dto;
  }

  @FXML
  private void nextStepAction() {
    ProgettoDto dto = getData();
    // TODO: implement next step action
  }

}
