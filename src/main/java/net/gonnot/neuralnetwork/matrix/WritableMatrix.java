package net.gonnot.neuralnetwork.matrix;
public interface WritableMatrix {
    void setValue(int row, int column, double value);

    void setValue(int index, double value);

    Matrix toMatrix();

    static WritableMatrix matrix(int rowCount, int columnCount) {
        return new BasicWritableMatrix(rowCount, columnCount);
    }
}
