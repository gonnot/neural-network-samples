package net.gonnot.neuralnetwork.operation;
import java.util.stream.IntStream;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.WritableMatrix;
@SuppressWarnings("WeakerAccess")
public class Operation {
    public static Matrix transpose(Matrix matrix) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                return matrix.value(column, row);
            }


            @Override
            public double value(int index) {
                throw new UnsupportedOperationException();
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
            public double value(int index) {
                return matrix.value(index) * value;
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


    public static Matrix powerBy(double value, Matrix matrix) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                return Math.pow(matrix.value(row, column), value);
            }


            @Override
            public double value(int index) {
                throw new UnsupportedOperationException();
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
            public double value(int index) {
                throw new UnsupportedOperationException();
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


    public static Matrix mergeLeftRight(Matrix left, Matrix right) {
        if (left.rows() != right.rows()) {
            throw new IllegalArgumentException(String.format("failed to merge -> left(%d, %d) | right(%d, %d)", left.rows(), left.columns(), right.rows(), right.columns()));
        }
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                if (column <= left.columns()) {
                    return left.value(row, column);
                }
                return right.value(row, column - left.columns());
            }


            @Override
            public double value(int index) {
                throw new UnsupportedOperationException();
            }


            @Override
            public int columns() {
                return left.columns() + right.columns();
            }


            @Override
            public int rows() {
                return left.rows();
            }
        };
    }


    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        WritableMatrix result = WritableMatrix.matrix(matrixA.rows(), matrixB.columns());

        if (matrixB.columns() > 1) {
            IntStream.rangeClosed(1, matrixA.rows()).parallel().forEach(r -> {
                for (int c = 1; c <= matrixB.columns(); c++) {
                    result.setValue(r, c, compute(r, c, matrixA, matrixB));
                }
            });
            return result.toMatrix();
        }

        int indexA = 0;
        int cIndex = 0;
        double b0 = matrixB.value(0);
        double b1 = matrixB.value(1);
        double b2 = 0;
        boolean pasTheta = matrixA.columns() > 2;
        if (pasTheta) {
            b2 = matrixB.value(2);
        }
        for (int i = 0; i < matrixA.rows(); i++) {
            double total = matrixA.value(indexA++) * b0;
            total += matrixA.value(indexA++) * b1;

            if (pasTheta) {
                total += matrixA.value(indexA++) * b2;
                for (int j = 3; j < matrixA.columns(); j++) {
                    total += matrixA.value(indexA++) * matrixB.value(j);
                }
            }

            result.setValue(cIndex++, total);
        }

        return result.toMatrix();
    }


    private static double compute(int row, int column, final Matrix matrixA, final Matrix matrixB) {
        double result = 0.;
        for (int i = 1; i <= matrixB.rows(); i++) {
            double valueB = matrixB.value(i, column);
            double valueA = matrixA.value(row, i);
            result += valueB * valueA;
        }
        return result;
    }


    public static Matrix minus(Matrix matrixA, Matrix matrixB) {
        return new Matrix() {
            @Override
            public double value(int row, int column) {
                return matrixA.value(row, column) - matrixB.value(row, column);
            }


            @Override
            public double value(int index) {
                return matrixA.value(index) - matrixB.value(index);
            }


            @Override
            public int columns() {
                return matrixA.columns();
            }


            @Override
            public int rows() {
                return matrixA.rows();
            }
        };
    }


    public static String toString(Matrix matrix) {
        if (matrix.columns() == 0 && matrix.rows() == 0) {
            return "empty Matrix";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= matrix.rows(); i++) {
            for (int j = 1; j <= matrix.columns(); j++) {
                builder.append("    ").append(matrix.value(i, j));
            }
            builder.append('\n');
        }

        return builder.toString();
    }


    public static Matrix flatten(Matrix matrixA) {
        WritableMatrix result = WritableMatrix.matrix(matrixA.rows(), matrixA.columns());

        IntStream.rangeClosed(1, matrixA.rows()).parallel().forEach(r -> {
            for (int c = 1; c <= matrixA.columns(); c++) {
                result.setValue(r, c, matrixA.value(r, c));
            }
        });

        return result.toMatrix();
    }


    public static SubMatrixBuilder subMatrix(Matrix matrix) {
        return new SubMatrixBuilder(matrix);
    }


    public static double sum(Matrix matrix) {
        double sum = 0;
        for (int i = 1; i <= matrix.rows(); i++) {
            sum += matrix.value(i, 1);
        }
        return sum;
    }


    public static class SubMatrixBuilder {
        private final Matrix matrix;


        public SubMatrixBuilder(Matrix matrix) {
            this.matrix = matrix;
        }


        public SubMatrixBuilderFinalPhase rows(int from, int to) {
            return new SubMatrixBuilderFinalPhase(matrix, from, to);
        }


        public SubMatrixBuilderFinalPhase rows(int to) {
            return rows(1, to);
        }


        public SubMatrixBuilderFinalPhase allRows() {
            return rows(1, -1);
        }
    }
    public static class SubMatrixBuilderFinalPhase {
        private final Matrix matrix;
        private final int fromRow;
        private final int toRow;


        public SubMatrixBuilderFinalPhase(Matrix matrix, int fromRow, int toRow) {
            this.matrix = matrix;
            this.fromRow = fromRow;
            this.toRow = toRow;
        }


        public Matrix columns(int from, int to) {
            return new SubMatrix(matrix, fromRow, toRow, from, to);
        }


        public Matrix columns(int to) {
            return columns(1, to);
        }


        public Matrix allColumns() {
            return columns(1, -1);
        }
    }
    private static class SubMatrix implements Matrix {
        private final Matrix matrix;
        private final int fromRow;
        private final int toRow;
        private final int fromColumn;
        private final int toColumn;


        public SubMatrix(Matrix matrix, int fromRow, int toRow, int fromColumn, int toColumn) {
            this.matrix = matrix;
            this.fromRow = fromRow;
            this.toRow = toRow >= 0 ? toRow : matrix.rows();
            this.fromColumn = fromColumn;
            this.toColumn = toColumn >= 0 ? toColumn : matrix.columns();

            if (this.toColumn > this.matrix.columns() || this.fromColumn > this.toColumn) {
                throw new IllegalArgumentException(String.format("Column Range out of bounds. Specified [%d..%d] but Matrix is [1..%d]", this.fromColumn, this.toColumn, this.matrix.columns()));
            }
            if (this.toRow > this.matrix.rows() || this.fromRow > this.toRow) {
                throw new IllegalArgumentException(String.format("Row Range out of bounds. Specified [%d..%d] but Matrix is [1..%d]", this.fromRow, this.toRow, this.matrix.rows()));
            }
        }


        @Override
        public double value(int row, int column) {
            return matrix.value(row + fromRow - 1, column + fromColumn - 1);
        }


        @Override
        public double value(int index) {
            throw new UnsupportedOperationException();
        }


        @Override
        public int columns() {
            return toColumn - fromColumn + 1;
        }


        @Override
        public int rows() {
            return toRow - fromRow + 1;
        }
    }
}
