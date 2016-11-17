package net.gonnot.neuralnetwork.matrix;
class BasicWritableMatrix implements WritableMatrix {
    private final int rowCount;
    private final int columnCount;
    private double[] content;
    private boolean contentIsReadOnly = false;


    BasicWritableMatrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        content = new double[rowCount * columnCount];
    }


    @Override
    public void setValue(int row, int column, double value) {
        if (row > rowCount || column > columnCount) {
            throw new ArrayIndexOutOfBoundsException(String.format("Indices (%d,%d) is out of bounds. Matrix size is (1..%d , 1..%d))", row, column, rowCount, columnCount));
        }
        if (contentIsReadOnly) {
            double[] newContent = new double[content.length];
            System.arraycopy(content, 0, newContent, 0, content.length);
            content = newContent;
            contentIsReadOnly = false;
        }
        content[BasicMatrix.convertToSingleDimensionalArrayIndex(row, column, columnCount)] = value;
    }


    @Override
    public Matrix toMatrix() {
        contentIsReadOnly = true;
        return Matrix.matrix(columnCount, content);
    }
}
