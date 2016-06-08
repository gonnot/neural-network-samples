package net.gonnot.neuralnetwork.matrix;

class VectorMatrix implements Matrix {
    private final double[] vector;


    VectorMatrix(double[] vector) {
        this.vector = vector;
    }


    @Override
    public double value(int row, int column) {
        return vector[row - 1];
    }


    @Override
    public int columns() {
        return (vector.length == 0) ? 0 : 1;
    }


    @Override
    public int rows() {
        return vector.length;
    }
}
