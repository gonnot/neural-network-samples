package net.gonnot.neuralnetwork.matrix;
class OneValueMatrix implements Matrix {
    private final int rows;
    private final int columns;
    private final double cellsValue;


    OneValueMatrix(int rows, int columns, double cellsValue) {
        this.rows = rows;
        this.columns = columns;
        this.cellsValue = cellsValue;
    }


    @Override
    public double value(int row, int column) {
        return cellsValue;
    }


    @Override
    public double value(int index) {
        return cellsValue;
    }


    @Override
    public int columns() {
        return columns;
    }


    @Override
    public int rows() {
        return rows;
    }
}
