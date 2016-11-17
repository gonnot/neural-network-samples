package net.gonnot.neuralnetwork.matrix;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class WritableMatrixTest {
    @Test
    public void test_newMatrixContainsZeroValues() throws Exception {
        WritableMatrix matrix = WritableMatrix.matrix(2, 3);

        assertThat(toMatrix(matrix)).containsExactly(new double[]{0, 0, 0},
                                                     new double[]{0, 0, 0});
    }


    @Test
    public void test_matrixIsWritable_leftTopElement() throws Exception {
        WritableMatrix matrix = WritableMatrix.matrix(2, 3);

        matrix.setValue(1, 1, 1.1);

        assertThat(toMatrix(matrix)).containsExactly(new double[]{1.1, 0, 0},
                                                     new double[]{0, 0, 0});
    }


    @Test
    public void test_matrixIsWritable_rightBottomElement() throws Exception {
        WritableMatrix matrix = WritableMatrix.matrix(2, 3);

        matrix.setValue(2, 3, 2.3);

        assertThat(toMatrix(matrix)).containsExactly(new double[]{0, 0, 0},
                                                     new double[]{0, 0, 2.3});
    }


    @Test
    public void test_setValueOutsideMatrix() throws Exception {
        WritableMatrix matrix = WritableMatrix.matrix(3, 2);

        MatrixTest.assertFailure(() -> matrix.setValue(4, 2, -1), "Indices (4,2) is out of bounds. Matrix size is (1..3 , 1..2))");
        MatrixTest.assertFailure(() -> matrix.setValue(1, 4, -1), "Indices (1,4) is out of bounds. Matrix size is (1..3 , 1..2))");
    }


    @Test
    public void test_toMatrixReturnsAnImmutableObject() throws Exception {
        WritableMatrix matrix = WritableMatrix.matrix(2, 2);

        Matrix beforeSetValue = matrix.toMatrix();

        matrix.setValue(1, 1, 1.1);

        assertThat(MatrixLoaderTest.toMatrix(beforeSetValue)).containsExactly(new double[]{0, 0},
                                                                              new double[]{0, 0});
    }


    private static double[][] toMatrix(WritableMatrix matrix) {
        return MatrixLoaderTest.toMatrix(matrix.toMatrix());
    }
}