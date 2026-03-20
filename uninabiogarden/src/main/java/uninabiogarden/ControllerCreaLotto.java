package uninabiogarden;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Orto;
import uninabiogarden.entities.Lotto.TipologiaTerreno;

public class ControllerCreaLotto {

  @FXML
  private VBox mainContent;

  @FXML
  private TextField codiceLottoInputField;

  @FXML
  private TextField estensioneLottoInputField;

  @FXML
  private TableView<Orto> availableOrtiTable;

  @FXML
  private TableColumn<Orto, String> nomeOrtoColumn;

  @FXML
  private TableColumn<Orto, String> indirizzoOrtoColumn;

  @FXML
  private Label nomeOrtoSelectedLabel;

  @FXML
  private Label indirizzoOrtoSelectedLabel;

  @FXML
  private ChoiceBox<String> tipologiaTerrenoChoiceBox;

  Long selectedOrtoId = null;

  @FXML
  private Label errorLabel;

  private ObservableList<Orto> ortiObservableList;

  @FXML
  public void initialize() {
    Utils.addCharacterLimit(codiceLottoInputField, 20);
    Utils.addDoubleFilter(estensioneLottoInputField, 10, 2);

    nomeOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeOrto"));
    indirizzoOrtoColumn.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));

    // Crea ObservableList dal model - sincronizzato con la lista degli orti
    ortiObservableList = FXCollections.observableList(MainController.getInstance().getOrti());
    availableOrtiTable.setItems(ortiObservableList);

    // Update labels when table selection changes
    availableOrtiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      if (newSel != null) {
        nomeOrtoSelectedLabel.setText(newSel.getNomeOrto());
        indirizzoOrtoSelectedLabel.setText(newSel.getFullAddress());
        selectedOrtoId = newSel.getId();
      } else {
        nomeOrtoSelectedLabel.setText("N/A");
        indirizzoOrtoSelectedLabel.setText("N/A");
        selectedOrtoId = null;
      }
    });

    for (Lotto.TipologiaTerreno tipo : Lotto.TipologiaTerreno.values()) {
      tipologiaTerrenoChoiceBox.getItems().add(tipo.name());
    }
    tipologiaTerrenoChoiceBox.setValue(Lotto.TipologiaTerreno.MEDIO_IMPASTO.name());

    clear();
  }

  private void clear() {
    errorLabel.setText("");
    nomeOrtoSelectedLabel.setText("N/A");
    indirizzoOrtoSelectedLabel.setText("N/A");
    selectedOrtoId = null;
    codiceLottoInputField.clear();
    estensioneLottoInputField.clear();
    tipologiaTerrenoChoiceBox.setValue(Lotto.TipologiaTerreno.MEDIO_IMPASTO.name());
    availableOrtiTable.getSelectionModel().clearSelection();
  }

  @FXML
  public void indietroAction() {
    clear();
    UIController.getInstance().openLottiView();
  }

  private Lotto getData() {
    Lotto lotto = new Lotto();
    lotto.setCodiceLotto(codiceLottoInputField.getText());
    try {
      lotto.setEstensioneMq(Double.parseDouble(estensioneLottoInputField.getText()));
    } catch (NumberFormatException e) {
      // leave null - validation will catch it
    }
    if (selectedOrtoId != null) {
      Orto proxyOrto = new Orto();
      proxyOrto.setId(selectedOrtoId);
      lotto.setOrto(proxyOrto);
    }
    lotto.setTipologiaTerreno(Lotto.TipologiaTerreno.valueOf(tipologiaTerrenoChoiceBox.getValue()));
    return lotto;
  }

  @FXML
  public void confermaAction() {
    Lotto lotto = getData();
    try {
      MainController.getInstance().creaLotto(lotto);
      clear();
      UIController.getInstance().openLottiView();
    } catch (Exception e) {
      System.err.println("Errore durante la creazione del lotto: " + e.getMessage());
      errorLabel.setText(e.getMessage());
    }
  }

}
