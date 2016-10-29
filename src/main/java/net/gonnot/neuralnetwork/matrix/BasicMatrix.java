package net.gonnot.neuralnetwork.matrix;
class BasicMatrix implements Matrix {
    private final double[] values;
    private final int columnCount;


    BasicMatrix(int columnCount, double[] values) {
        this.values = values;
        this.columnCount = columnCount;
    }


    @Override
    public double value(int row, int column) {
        return values[(row - 1) * columnCount + column - 1];
    }


    @Override
    public int columns() {
        if (values.length < columnCount) {
            return 0;
        }
        return columnCount;
    }


    @Override
    public int rows() {
        if (columnCount == 0) {
            return 0;
        }
        return values.length / columnCount;
    }
}
