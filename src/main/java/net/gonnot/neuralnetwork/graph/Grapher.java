package net.gonnot.neuralnetwork.graph;
import net.gonnot.neuralnetwork.matrix.Matrix;
/**
 *
 */
public class Grapher {

    public static PlotGraphBuilder plotGraph() {
        return new PlotGraphBuilder();
    }


    public static class PlotGraphBuilder {
        String title;
        String abscissLabel;
        Matrix absciss;
        String ordinateLabel;
        Matrix ordinate;
        String serieLabel;


        public PlotGraphBuilder title(String title) {
            this.title = title;
            return this;
        }


        public PlotGraphBuilder absciss(String label, Matrix vector) {
            this.abscissLabel = label;
            this.absciss = vector;
            return this;
        }


        public PlotGraphBuilder ordinate(String label, Matrix vector) {
            this.ordinateLabel = label;
            this.ordinate = vector;
            return this;
        }


        public void plot() {
            PlotGraph.go(this);
        }


        public PlotGraphBuilder seriesName(String serieLabel) {
            this.serieLabel = serieLabel;
            return this;
        }
    }
}
