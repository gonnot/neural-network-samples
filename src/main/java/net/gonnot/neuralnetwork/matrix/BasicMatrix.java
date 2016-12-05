package net.gonnot.neuralnetwork.matrix;
class BasicMatrix implements Matrix {
    private final double[] values;
    private final int columnCount;
    private final int rowCount;


    BasicMatrix(int columnCount, double[] values) {
        this.values = values;
        this.columnCount = values.length < columnCount ? 0 : columnCount;
        this.rowCount = columnCount == 0 ? 0 : values.length / columnCount;
    }


    @Override
    public double value(int row, int column) {
        if (row > rows() || column > columns()) {
            throw new ArrayIndexOutOfBoundsException(String.format("Indices (%d,%d) is out of bounds. Matrix size is (1..%d , 1..%d))", row, column, rows(), columns()));
        }
        return values[convertToSingleDimensionalArrayIndex(row, column, columnCount)];
    }


    @Override
    public double value(int index) {
        return values[index];
    }


    @Override
    public int columns() {
        return columnCount;
    }


    @Override
    public int rows() {
        return rowCount;
    }


    static int convertToSingleDimensionalArrayIndex(int row, int column, int columnCount) {
        return ((row - 1) * columnCount + (column - 1));
    }
}
