package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
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
  private boolean initNextStep = true;

  ProgettoDto progettoDto;

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

  public void init(ProgettoDto progettoDto) {
    if (progettoDto == null) {
      this.progettoDto = new ProgettoDto();
    }
    initNextStep = true;
    clear();

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
    if (progettoDto == null) {
      progettoDto = new ProgettoDto();
    }
    progettoDto.nome = nomeProgettoField.getText();
    progettoDto.descrizione = descrizioneField.getText();
    progettoDto.stato = "PIANIFICATO"; // Stato iniziale del progetto
    // le date sono impostate direttamente nel database
    progettoDto.lottoId = availableOrtiTable.getSelectionModel().getSelectedItem() != null
        ? availableOrtiTable.getSelectionModel().getSelectedItem().getId()
        : null;
    return progettoDto;
  }

  private String isValidData(ProgettoDto progettoDto) {
    if (progettoDto.nome == null || progettoDto.nome.isEmpty()) {
      return "Nome progetto mancante";
    }
    if (progettoDto.lottoId == null) {
      return "Lotto per il progetto non selezionato";
    }
    return null;
  }

  @FXML
  private void nextStepAction() {
    progettoDto = getData();
    String validationError = isValidData(progettoDto);
    if (validationError != null) {
      errorLabel.setText(validationError);
      return;
    }
    UIController.getInstance().openCreaProgettoStep2View(progettoDto, initNextStep);
    if (initNextStep) {
      initNextStep = false; // dopo il primo passaggio, non re-inizializzare i dati se si torna indietro al
                            // passo 1
    }
  }

}
