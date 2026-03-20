package uninabiogarden;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Color;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Report;
import javafx.util.StringConverter;
import javafx.embed.swing.SwingNode;

import uninabiogarden.dao.DatabaseController;

public class ControllerReport {

  @FXML
  private ChoiceBox<Lotto> lottiBox;

  @FXML
  private VBox mainContent;

  @FXML
  private Label numeroRaccolteLabel;

  @FXML
  private HBox spaceForReport;

  @FXML
  private Label ultimaRaccoltaLabel;

  @FXML
  private Label errorLabel;

  ArrayList<Report> reports = new ArrayList<>();
  Lotto lottoSelezionato;

  @FXML
  void initialize() {
    errorLabel.setText("");

    lottiBox.getItems().addAll(MainController.getInstance().getLotti());

    // per far vedere all utente il codice del lotto invece dell ogetto
    lottiBox.setConverter(new StringConverter<Lotto>() {
      @Override
      public String toString(Lotto l) {
        return (l == null) ? "" : l.getCodiceLotto();
      }

      @Override
      public Lotto fromString(String string) {
        return null;
      }
    });

    // valori di default
    numeroRaccolteLabel.setText("Numero Raccolte: 0");
    ultimaRaccoltaLabel.setText("Ultima Raccolta: N/A");

  }

  @FXML
  void generaReportAction(ActionEvent event) {

    // prendiamo il lotto selezionato
    Lotto lottoSelezionato = lottiBox.getValue();
    this.lottoSelezionato = lottoSelezionato;

    if (lottoSelezionato == null) {
      errorLabel.setText("Bisogna selezionare un lotto");
      return;
    }

    // query
    reports = MainController.getInstance().getDatabaseController().getReportDao().getRaccoltaByLotto(lottoSelezionato);

    // calcoliamo il num tot di raccolte
    int totRaccolte = 0;
    for (Report r : reports) {
      totRaccolte += r.getTotaleRaccolte();
    }
    numeroRaccolteLabel.setText("Numero totale raccolte: " + totRaccolte);

    // cerchiamo la data più recente tra tutte le colture
    LocalDate ultimaData = null;
    for (Report r : reports) {
      if (ultimaData == null || r.getUltimaRaccolta().isAfter(ultimaData)) {
        ultimaData = r.getUltimaRaccolta();
      }
    }
    ultimaRaccoltaLabel.setText("Ultima raccolta: " + (ultimaData != null ? ultimaData.toString() : "N/A"));

    JFreeChart chart = createBarChart(reports);
    displayChart(chart);

  }

  private JFreeChart createBarChart(List<Report> reports) {

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Report r : reports) {
      String nomeColtura = r.getNomeColtura();

      dataset.addValue(r.getMin(), "Minimo", nomeColtura);
      dataset.addValue(r.getMedia(), "Media", nomeColtura);
      dataset.addValue(r.getMax(), "Massimo", nomeColtura);
    }

    JFreeChart chart = ChartFactory.createBarChart(
        "Report Dettagliato Lotto: " + lottoSelezionato.getCodiceLotto(),
        "Coltura",
        "Quantità (kg)",
        dataset);

    // Styling
    CategoryPlot plot = chart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();

    renderer.setSeriesPaint(0, Color.RED);
    renderer.setSeriesPaint(1, Color.BLUE);
    renderer.setSeriesPaint(2, Color.GREEN);

    renderer.setItemMargin(0.05);

    return chart;
  }

  private void displayChart(JFreeChart chart) {
    spaceForReport.getChildren().clear();

    ChartPanel chartPanel = new ChartPanel(chart);
    SwingNode swingNode = new SwingNode();
    swingNode.setContent(chartPanel);

    spaceForReport.getChildren().add(swingNode);
  }

  @FXML
  void indietroAction(ActionEvent event) {
    UIController.getInstance().openProprietarioHomeView();
  }

}