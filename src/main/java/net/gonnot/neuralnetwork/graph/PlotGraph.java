package net.gonnot.neuralnetwork.graph;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.gonnot.neuralnetwork.graph.Grapher.PlotGraphBuilder;
class PlotGraph {

    private static void initAndShowGUI(PlotGraphBuilder config) {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame("Plot");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Platform.runLater(() -> initFX(fxPanel, config));
    }


    private static void initFX(JFXPanel fxPanel, PlotGraphBuilder config) {
        // This method is invoked on the JavaFX thread
        BorderPane root = new BorderPane();
        root.setCenter(createChart(config));
        Scene scene = new Scene(root);
        fxPanel.setScene(scene);
    }


    private static Node createChart(PlotGraphBuilder config) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final ScatterChart<Number, Number> scatterChart = new
              ScatterChart<>(xAxis, yAxis);

        scatterChart.setTitle(config.title);
        xAxis.setLabel(config.abscissLabel);
        yAxis.setLabel(config.ordinateLabel);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(config.serieLabel);
        for (int i = 1; i <= config.absciss.rows(); i++) {
            series1.getData().add(new XYChart.Data(config.absciss.value(i, 1), config.ordinate.value(i, 1)));
        }

        scatterChart.getData().addAll(series1);
        return scatterChart;
    }


    static void go(PlotGraphBuilder config) {
        SwingUtilities.invokeLater(() -> initAndShowGUI(config));
    }
}
