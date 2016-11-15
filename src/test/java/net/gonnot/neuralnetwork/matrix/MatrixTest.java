package net.gonnot.neuralnetwork.matrix;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static net.gonnot.neuralnetwork.matrix.MatrixLoaderTest.toMatrix;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(Enclosed.class)
public class MatrixTest {

    public static class VectorUseCases {
        @Test
        public void test_emptyMatrix() throws Exception {
            Matrix ones = Matrix.vector(new double[0]);
            assertThat(ones.columns()).isEqualTo(0);
            assertThat(ones.rows()).isEqualTo(0);
        }


        @Test
        public void test_getValue() throws Exception {
            Matrix ones = Matrix.vector(new double[]{0., 1., 2.});

            assertThat(ones.columns()).isEqualTo(1);
            assertThat(ones.rows()).isEqualTo(3);

            assertThat(ones.value(1, 1)).isEqualTo(0.);
            assertThat(ones.value(2, 1)).isEqualTo(1.);
            assertThat(ones.value(3, 1)).isEqualTo(2.);
        }
    }

    public static class MatrixOneUseCases {
        @Test
        public void test_emptyMatrix() throws Exception {
            Matrix ones = Matrix.ones(0, 0);
            assertThat(ones.columns()).isEqualTo(0);
            assertThat(ones.rows()).isEqualTo(0);
        }


        @Test
        public void test_getValue() throws Exception {
            Matrix ones = Matrix.ones(1, 1);
            assertThat(ones.value(1, 1)).isEqualTo(1.);
        }
    }

    public static class MatrixZeroUseCases {
        @Test
        public void test_emptyMatrix() throws Exception {
            Matrix ones = Matrix.zeros(0, 0);
            assertThat(ones.columns()).isEqualTo(0);
            assertThat(ones.rows()).isEqualTo(0);
        }


        @Test
        public void test_getValue() throws Exception {
            Matrix ones = Matrix.zeros(1, 1);
            assertThat(ones.value(1, 1)).isEqualTo(0.);
        }
    }

    public static class TwoDimensionsUseCases {
        @Test
        public void test_emptyMatrix() throws Exception {
            Matrix matrix = Matrix.matrix(0, new double[0]);
            assertThat(matrix.columns()).isEqualTo(0);
            assertThat(matrix.rows()).isEqualTo(0);
        }


        @Test
        public void test_getValue() throws Exception {
            Matrix matrix = Matrix.matrix(2, new double[]{
                  1.1, 1.2,
                  2.1, 2.2
            });

            assertThat(matrix.columns()).isEqualTo(2);
            assertThat(matrix.rows()).isEqualTo(2);

            assertThat(matrix.value(1, 1)).isEqualTo(1.1);
            assertThat(matrix.value(1, 2)).isEqualTo(1.2);
            assertThat(matrix.value(2, 2)).isEqualTo(2.2);
        }


        @Test
        public void test_buildMatrixFromTwoDimensions() throws Exception {
            double[][] content = {
                  {1.1, 1.2, 1.3},
                  {2.1, 2.2, 2.3}
            };
            Matrix matrix = Matrix.matrix(content);

            assertThat(toMatrix(matrix)).containsExactly(new double[]{1.1, 1.2, 1.3},
                                                         new double[]{2.1, 2.2, 2.3}
            );
        }
    }

    public static class ErrorUseCases {
        @Test
        public void test_getValueOutsideMatrix() throws Exception {
            double[][] content = {
                  {1.1, 1.2},
                  {2.1, 2.2},
                  {3.1, 3.2}
            };
            Matrix matrix = Matrix.matrix(content);

            try {
                matrix.value(4, 2);
                fail("Should throw an error");
            }
            catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("Indices (4,2) is out of bounds. Matrix size is (1..3 , 1..2))");
            }

            try {
                matrix.value(1, 4);
                fail("Should throw an error");
            }
            catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("Indices (1,4) is out of bounds. Matrix size is (1..3 , 1..2))");
            }
        }
    }
}