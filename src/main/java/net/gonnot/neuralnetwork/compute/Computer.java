package net.gonnot.neuralnetwork.compute;
import net.gonnot.neuralnetwork.matrix.Matrix;
@SuppressWarnings("WeakerAccess")
public class Computer {
    public static Matrix transpose(Matrix matrix) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                return matrix.value(column, row);
            }


            @Override
            public int columns() {
                return matrix.rows();
            }


            @Override
            public int rows() {
                return matrix.columns();
            }
        };
    }


    public static Matrix multiplyBy(Double value, Matrix matrix) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                return matrix.value(row, column) * value;
            }


            @Override
            public int columns() {
                return matrix.columns();
            }


            @Override
            public int rows() {
                return matrix.rows();
            }
        };
    }


    public static Matrix mergeTopBottom(Matrix top, Matrix bottom) {
        if (top.columns() != bottom.columns()) {
            throw new IllegalArgumentException();
        }
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                if (row <= top.rows()) {
                    return top.value(row, column);
                }
                return bottom.value(row - top.rows(), column);
            }


            @Override
            public int columns() {
                return top.columns();
            }


            @Override
            public int rows() {
                return top.rows() + bottom.rows();
            }
        };
    }


    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                double result = 0.;
                for (int i = 1; i <= matrixB.rows(); i++) {
                    double valueB = matrixB.value(i, column);
                    double valueA = matrixA.value(row, i);
                    result += valueB * valueA;
                }
                return result;
            }


            @Override
            public int columns() {
                return matrixB.columns();
            }


            @Override
            public int rows() {
                return matrixA.rows();
            }
        };
    }
}
