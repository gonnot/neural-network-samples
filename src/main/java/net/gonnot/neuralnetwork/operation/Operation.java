package net.gonnot.neuralnetwork.operation;
import java.util.stream.IntStream;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.WritableMatrix;
@SuppressWarnings("WeakerAccess")
public class Operation {
    public static Matrix transpose(Matrix matrix) {
        double[] content = new double[matrix.columns() * matrix.rows()];
        int index = 0;
        for (int column = 1; column <= matrix.columns(); column++) {
            for (int row = 1; row <= matrix.rows(); row++) {
                content[index++] = matrix.value(row, column);
            }
        }
        return Matrix.matrix(matrix.columns(), matrix.rows(), content);
    }


    public static Matrix multiplyBy(Double value, Matrix matrix) {
        double[] content = new double[matrix.columns() * matrix.rows()];
        int index = 0;
        for (int row = 1; row <= matrix.rows(); row++) {
            for (int column = 1; column <= matrix.columns(); column++) {
                content[index++] = matrix.value(row, column) * value;
            }
        }
        return Matrix.matrix(matrix.rows(), matrix.columns(), content);
    }


    public static Matrix powerBy(double value, Matrix matrix) {
        double[] content = new double[matrix.columns() * matrix.rows()];
        int index = 0;
        for (int row = 1; row <= matrix.rows(); row++) {
            for (int column = 1; column <= matrix.columns(); column++) {
                double originalValue = matrix.value(row, column);
                if (value == 2) {
                    content[index++] = originalValue * originalValue;
                }
                else {
                    content[index++] = Math.pow(originalValue, value);
                }
            }
        }
        return Matrix.matrix(matrix.rows(), matrix.columns(), content);
    }


    public static Matrix mergeTopBottom(Matrix top, Matrix bottom) {
        if (top.columns() != bottom.columns()) {
            throw new IllegalArgumentException();
        }
        final int finalRowCount = top.rows() + bottom.rows();
        double[] content = new double[top.columns() * finalRowCount];
        int index = 0;
        for (int row = 1; row <= top.rows(); row++) {
            for (int column = 1; column <= top.columns(); column++) {
                content[index++] = top.value(row, column);
            }
        }
        for (int row = 1; row <= bottom.rows(); row++) {
            for (int column = 1; column <= bottom.columns(); column++) {
                content[index++] = bottom.value(row, column);
            }
        }
        return Matrix.matrix(finalRowCount, top.columns(), content);
    }


    public static Matrix mergeLeftRight(Matrix left, Matrix right) {
        if (left.rows() != right.rows()) {
            throw new IllegalArgumentException(String.format("failed to merge -> left(%d, %d) | right(%d, %d)", left.rows(), left.columns(), right.rows(), right.columns()));
        }
        final int finalColumnCount = left.columns() + right.columns();
        double[] content = new double[finalColumnCount * left.rows()];
        int index = 0;
        for (int row = 1; row <= left.rows(); row++) {
            for (int column = 1; column <= left.columns(); column++) {
                content[index++] = left.value(row, column);
            }
            for (int column = 1; column <= right.columns(); column++) {
                content[index++] = right.value(row, column);
            }
        }
        return Matrix.matrix(left.rows(), finalColumnCount, content);
    }


    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        WritableMatrix result = WritableMatrix.matrix(matrixA.rows(), matrixB.columns());

        IntStream.rangeClosed(1, matrixA.rows()).parallel().forEach(r -> {
            for (int c = 1; c <= matrixB.columns(); c++) {
                result.setValue(r, c, compute(r, c, matrixA, matrixB));
            }
        });

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
        double[] content = new double[matrixA.columns() * matrixA.rows()];
        int index = 0;
        for (int row = 1; row <= matrixA.rows(); row++) {
            for (int column = 1; column <= matrixA.columns(); column++) {
                content[index++] = matrixA.value(row, column) - matrixB.value(row, column);
            }
        }
        return Matrix.matrix(matrixA.rows(), matrixA.columns(), content);
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
