package uninabiogarden;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;

public class ControllerHome {

  @FXML
  private BorderPane mainContent;

  @FXML
  private VBox selectedContent;

  @FXML
  private Button dashboardButton;

  @FXML
  private Button ortiButton;

  @FXML
  private Button lottiButton;

  @FXML
  private Button progettiButton;

  @FXML
  private Button notificheButton;

  @FXML
  private Button reportButton;

  public void openForColtivatore() {
    dashboardButton.setVisible(false);
    dashboardButton.setManaged(false);
    ortiButton.setVisible(false);
    ortiButton.setManaged(false);
    lottiButton.setVisible(false);
    lottiButton.setManaged(false);
    reportButton.setVisible(false);
    reportButton.setManaged(false);

    progettiAction(null);
  }

  public void openForProprietario() {
    dashboardButton.setVisible(true);
    dashboardButton.setManaged(true);
    ortiButton.setVisible(true);
    ortiButton.setManaged(true);
    lottiButton.setVisible(true);
    lottiButton.setManaged(true);
    reportButton.setVisible(true);
    reportButton.setManaged(true);

    UIController.getInstance().openDashboardView();
  }

  Pane getSelectedContent() {
    return selectedContent;
  }

  @FXML
  private void profiloAction(ActionEvent event) {
    UIController.getInstance().openProfiloView();
  }

  @FXML
  private void dashboardAction(ActionEvent event) {
    if (MainController.getInstance().getUtenteLoggato() instanceof Coltivatore) {
      UIController.getInstance().openColtivatoreHomeView();
    } else {
      UIController.getInstance().openProprietarioHomeView();
    }
  }

  @FXML
  private void progettiAction(ActionEvent event) {
    UIController.getInstance().openProgettiView();
  }

  @FXML
  private void notificheAction(ActionEvent event) {
    UIController.getInstance().openNotificheView();
  }

  @FXML
  private void lottiAction(ActionEvent event) {
    UIController.getInstance().openLottiView();
  }

  @FXML
  private void ortiAction(ActionEvent event) {
    UIController.getInstance().openOrtiView();
  }

  @FXML
  private void ReportAction(ActionEvent event) {
    UIController.getInstance().openReportView();
  }

}
