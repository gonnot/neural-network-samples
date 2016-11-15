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
import net.gonnot.neuralnetwork.matrix.Matrix;
/**
 *
 */
public class Grapher {

    public static void plot(Matrix matrix) {
        PlotGraph.go(matrix);
    }


    private static class PlotGraph {

        private static void initAndShowGUI(Matrix matrix) {
            // This method is invoked on the EDT thread
            JFrame frame = new JFrame("Swing and JavaFX");
            final JFXPanel fxPanel = new JFXPanel();
            frame.add(fxPanel);
            frame.setSize(600, 600);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            Platform.runLater(() -> initFX(fxPanel, matrix));
        }


        private static void initFX(JFXPanel fxPanel, Matrix matrix) {
            // This method is invoked on the JavaFX thread
            BorderPane root = new BorderPane();
            root.setCenter(createChart(matrix));
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
        }


        private static Node createChart(Matrix matrix) {
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();

            final ScatterChart<Number, Number> scatterChart = new
                  ScatterChart<>(xAxis, yAxis);

            scatterChart.setTitle("Investment Overview");
            xAxis.setLabel("Age (years)");
            yAxis.setLabel("Returns to date");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Equities");
            for (int i = 1; i <= matrix.rows(); i++) {
                series1.getData().add(new XYChart.Data(matrix.value(i, 1), matrix.value(i, 2)));
            }

            scatterChart.getData().addAll(series1);
            return scatterChart;
        }


        static void go(Matrix matrix) {
            SwingUtilities.invokeLater(() -> initAndShowGUI(matrix));
        }
    }
}
