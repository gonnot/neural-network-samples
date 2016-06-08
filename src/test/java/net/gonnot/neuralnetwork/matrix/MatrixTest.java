package net.gonnot.neuralnetwork.matrix;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
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
            Matrix ones = Matrix.vector(new double[] {0., 1., 2.});

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
}