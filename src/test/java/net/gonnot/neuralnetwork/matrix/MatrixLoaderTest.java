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
        // todo to-be-removed
        Double[] matrix = MatrixLoader.load(Paths.get("data/matrix-y.txt"));

        assertThat(matrix).containsOnly(10., 1., 2., 3., 4., 5., 6., 7., 8., 9.);
    }


    @Test
    public void testGivenTwoLinesWhenStreamToMatrixThenGetVector() throws Exception {
        Stream<String> stream = Stream.of("0.0", " 1.0 ");
        Double[] matrix = MatrixLoader.streamToMatrix(stream);

        assertThat(matrix).containsExactly(0.0, 1.0);
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
}