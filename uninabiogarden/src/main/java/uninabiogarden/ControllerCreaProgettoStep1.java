package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ControllerCreaProgettoStep1 {

  @FXML
  private VBox mainContent;

  @FXML
  private TableView<?> availableOrtiTable;

  @FXML
  private TableColumn<?, ?> nomeOrtoColumn;

  @FXML
  private TableColumn<?, ?> indirizzoOrtoColumn;

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

  private ObservableList<Lotto> availableLottiObsList = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    Utils.addCharacterLimit(nomeProgettoField, 100);
    Utils.addCharacterLimit(descrizioneField, 500);
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
      availableLottiObsList.setAll(MainController.getInstance().getAvailableLotti());
      if (availableLottiObsList.isEmpty()) {
        errorLabel.setText("Non ci sono lotti disponibili al momento");
      }
    } catch (Exception e) {
      System.err.println("Errore durante il caricamento dei lotti disponibili: " + e.getMessage());
      errorLabel.setText("Errore durante il caricamento dei lotti disponibili. Riprova più tardi.");
    }
  }

  @FXML
  private void indietroAction() {
    // TODO: implement navigation to previous view
  }

  @FXML
  private void nextStepAction() {
    // TODO: implement next step action
  }

}
