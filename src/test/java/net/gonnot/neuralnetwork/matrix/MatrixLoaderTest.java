package net.gonnot.neuralnetwork.matrix;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 */
public class MatrixLoaderTest {

    @Test
    public void testFileStreamToMatrix() throws Exception {
        Matrix matrix = MatrixLoader.load(Paths.get("data/matrix-y.txt"));

        assertThat(toArray(matrix)).containsOnly(10., 1., 2., 3., 4., 5., 6., 7., 8., 9.);
    }


    @Test
    public void testGivenTwoLinesWhenStreamToMatrixThenGetVector() throws Exception {
        Stream<String> stream = Stream.of("0.0", " 1.0 ");
        Matrix matrix = MatrixLoader.streamToMatrix(stream);

        assertThat(toMatrix(matrix)).containsExactly(new double[]{0.0},
                                                     new double[]{1.0});
    }


    @Test
    public void testGivenTwoLinesWhenStreamToMatrixThenGetTwoDimensionsMatrix() throws Exception {
        Stream<String> stream = Stream.of("1.1,1.2", " 2.1,2.2");
        Matrix matrix = MatrixLoader.streamToMatrix(stream);

        assertThat(toMatrix(matrix)).containsExactly(new double[]{1.1, 1.2},
                                                     new double[]{2.1, 2.2});
    }


    @Test
    public void testConvertToDouble() throws Exception {
        assertThat(MatrixLoader.toDouble("1.00000000e+01")).isEqualTo(10.);
        assertThat(MatrixLoader.toDouble("1.00000000e+00")).isEqualTo(1.);
    }


    @Test(expected = NumberFormatException.class)
    public void testGivenBadValueWhenConvertToDoubleThenFails() throws Exception {
        assertThat(MatrixLoader.toDouble("not a double")).isEqualTo(10.);
    }


    private double[][] toMatrix(Matrix matrix) {
        double[][] result = new double[matrix.rows()][matrix.columns()];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = matrix.value(i + 1, j + 1);
            }
        }
        return result;
    }


    private double[] toArray(Matrix matrix) {
        double[] result = new double[matrix.rows() * matrix.columns()];
        for (int row = 0; row < matrix.rows(); row++) {
            for (int col = 0; col < matrix.columns(); col++) {
                int index = (row * matrix.columns()) + col;
                result[index] = matrix.value(row + 1, col + 1);
            }
        }
        return result;
    }
}