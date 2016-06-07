package net.gonnot.neuralnetwork;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class ApplicationFrame extends Application {

    @Override
    public final void start(Stage primaryStage) {
        Button launchTrainingButton = new Button();
        launchTrainingButton.setText("Launch Training");
        launchTrainingButton.setOnAction(event -> System.exit(0));

        BorderPane root = new BorderPane();
        root.setTop(launchTrainingButton);
        root.setCenter(createCostLineChart());

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private LineChart<Number, Number> createCostLineChart() {
        //defining the axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        yAxis.setLabel("Cost");
        xAxis.setLabel("Iteration");

        //creating the chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Cost Value by Iteration");

        lineChart.getData().add(createSampleCostRun());

        return lineChart;
    }


    private static Series createSampleCostRun() {
        //defining a series
        Series series = new Series();
        series.setName("run 1");

        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        return series;
    }
}