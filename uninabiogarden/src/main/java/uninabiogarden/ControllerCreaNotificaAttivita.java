package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Notifica;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Attivita;

public class ControllerCreaNotificaAttivita {

    @FXML
    private TableView<Attivita> attivitaTable;

    @FXML
    private TableColumn<Progetto, String> dataInizioProgettoColonna;

    @FXML
    private TextArea descrizioneField;

    @FXML
    private TableColumn<Progetto, String> descrizioneProgettoColonna;

    @FXML
    private Label errorLable;

    @FXML
    private VBox mainContent;

    @FXML
    private TextField nomeField;

    @FXML
    private TableColumn<Progetto, String> nomeProgettoColonna;

    @FXML
    private TableColumn<Attivita, String> noteTecnicheAttivitaColonna;

    @FXML
    private TableView<Progetto> progettoTable;

    @FXML
    private TableColumn<Attivita, String> statoAttivitaColonna;

    @FXML
    private TableColumn<Progetto, String> statoProgettoColonna;

    @FXML
    private TableColumn<Attivita, String> titoloAttivitaColonna;

    @FXML
    private ChoiceBox<Notifica.Urgenza> urgenzaField;

    @FXML
    void initialize() {

        urgenzaField.getItems().addAll(Notifica.Urgenza.values());
        urgenzaField.setValue(Notifica.Urgenza.BASSA);
        
        Utils.addCharacterLimit(nomeField, 50);
        Utils.addCharacterLimit(descrizioneField, 500);

        errorLable.setText("");
 
    }

    @FXML
    void indietroAction(ActionEvent event) {
        clearForm();
        UIController.getInstance().openNotificheView();
    }

    @FXML
    void prossimoStepAction(ActionEvent event) {

        if(isFormInvalid()) {
            return;
        }

        Notifica ntf = new Notifica();
        getData(ntf);

        UIController.getInstance().openCreaNotificheStep2View(/* ntf */);

        //for testign
        System.out.println(ntf.toString());
    }

    boolean isFormInvalid() { 
    if (nomeField.getText().trim().isEmpty()) {
        errorLable.setText("Il campo nome è obbligatorio");
        return true; 
    }
    if (descrizioneField.getText().trim().isEmpty()) {
        errorLable.setText("Il campo descrizione è obbligatorio");
        return true; 
    }
    if (progettoTable.getSelectionModel().getSelectedItem() == null) {
        errorLable.setText("Seleziona un progetto dalla tabella");
        return true;
    }
    if (attivitaTable.getSelectionModel().getSelectedItem() == null) {
        errorLable.setText("Seleziona un'attivita dalla tabella");
        return true;
    }
    return false; 
}

    void clearForm() {
        nomeField.clear();
        descrizioneField.clear();
        urgenzaField.setValue(Notifica.Urgenza.BASSA);
        errorLable.setText("");
    }

    void getData(Notifica notifica) {
        notifica.setNome(nomeField.getText());
        notifica.setDescrizione(descrizioneField.getText());
        notifica.setUrgenza((Notifica.Urgenza) urgenzaField.getValue());
        notifica.setTipo(Notifica.Tipo.NOTIFICA_ATTIVITA_IMMINENTE);
        notifica.setMittente((Proprietario) MainController.getInstance().getUtenteLoggato());

        Progetto prg = progettoTable.getSelectionModel().getSelectedItem();
        if (prg != null) {
            notifica.setProgetto(prg);
        }
    }

}
