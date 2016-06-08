package net.gonnot.neuralnetwork.matrix;
/**
 *
 */
public interface Matrix {
    double value(int row, int column);

    int columns();

    int rows();

    static Matrix ones(int rows, int columns) {
        return new OneValueMatrix(rows, columns, 1.);
    }

    static Matrix zeros(int rows, int columns) {
        return new OneValueMatrix(rows, columns, 0.);
    }

    static Matrix vector(double[] values) {
        return new VectorMatrix(values);
    }
}
