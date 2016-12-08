package net.gonnot.neuralnetwork.exercise;
import java.io.IOException;
import java.nio.file.Paths;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.MatrixLoader;
import net.gonnot.neuralnetwork.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import static net.gonnot.neuralnetwork.exercise.Exercise1.computeCost;
import static net.gonnot.neuralnetwork.exercise.Exercise1.gradientDescent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
/**
 *
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class Exercise1Test {

    private Matrix X;
    private Matrix y;
    private Matrix theta;


    @Before
    public void setup() throws IOException {
        Matrix data = MatrixLoader.load(Paths.get("data/exercise1/data.txt"));

        X = Operation.subMatrix(data).allRows().columns(1, 1);
        y = Operation.subMatrix(data).allRows().columns(2, 2);

        int m = y.rows();

        X = Operation.mergeLeftRight(Matrix.ones(m, 1), X);
        theta = Matrix.zeros(2, 1);
    }


    @Test
    public void test_costFunction() throws Exception {
        double cost = computeCost(X, y, theta);

        assertThat(cost).isCloseTo(32.07, offset(0.01));
    }


    @Test
    public void test_gradientDescent() throws Exception {
        long begin = System.currentTimeMillis();

        theta = gradientDescent(X, y, theta, 0.01, 1500, null);

        assertThat(theta.value(1, 1)).isCloseTo(-3.630, offset(0.001));
        assertThat(theta.value(2, 1)).isCloseTo(1.166, offset(0.001));

        long end = System.currentTimeMillis();

        System.out.println("Time --> " + (end - begin) + " ms");

        assertThat(computeCost(X, y, theta)).isCloseTo(4.483, offset(0.001));
    }
}