package net.gonnot.neuralnetwork.compute;
import net.gonnot.neuralnetwork.matrix.Matrix;
import org.junit.Test;
import static net.gonnot.neuralnetwork.matrix.MatrixLoaderTest.toMatrix;
import static org.assertj.core.api.Assertions.assertThat;
public class ComputerTest {
    @Test
    public void testTransposeMatrix() throws Exception {
        Matrix matrix = Matrix.matrix(3, new double[]{
              1.1, 1.2, 1.3,
              2.1, 2.2, 2.3
        });

        Matrix result = Computer.transpose(matrix);

        assertThat(toMatrix(result)).containsExactly(new double[]{1.1, 2.1},
                                                     new double[]{1.2, 2.2},
                                                     new double[]{1.3, 2.3}
        );
    }


    @Test
    public void testMultiplyBy() throws Exception {
        Matrix matrix = Matrix.matrix(
              new double[][]{
                    {1.1, 1.2},
                    {2.1, 2.2}
              });

        Matrix result = Computer.multiplyBy(10., matrix);

        assertThat(toMatrix(result)).containsExactly(new double[]{11., 12.},
                                                     new double[]{21., 22.});
    }
}

