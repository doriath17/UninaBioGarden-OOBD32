package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;

public class ControllerDashboard {

  @FXML
  private VBox mainContent;

  @FXML
  private Label messaggiobenvenuto;

  @FXML
  private LineChart<String, Number> reportChart;

  @FXML
  private TableView<?> notificheTable;

  @FXML
  private TableView<?> ProgettiTable;

  @FXML
  private void initialize() {
    // initialization logic if needed
  }

}
