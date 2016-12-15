package net.gonnot.neuralnetwork.matrix;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;
public interface Matrix {
    double value(int row, int column);

    double value(int index);

    int columns();

    int rows();

    static Matrix ones(int rows, int columns) {
        return new OneValueMatrix(rows, columns, 1.);
    }

    static Matrix zeros(int rows, int columns) {
        return new OneValueMatrix(rows, columns, 0.);
    }

    static Matrix vector(double[] values) {
        return matrix(1, values);
    }

    static Matrix matrix(int columnCount, double[] values) {
        return new BasicMatrix(values, columnCount == 0 ? 0 : values.length / columnCount, values.length < columnCount ? 0 : columnCount);
    }

    static Matrix matrix(int rowCount, int columnCount, double[] values) {
        return new BasicMatrix(values, rowCount, columnCount);
    }

    static Matrix matrix(double[][] contentInTwoDimensions) {
        int columnCount = contentInTwoDimensions[0].length;
        double[] doubles = Stream.of(contentInTwoDimensions)
              .flatMapToDouble(DoubleStream::of)
              .toArray();
        return matrix(columnCount, doubles);
    }
}
