package net.gonnot.neuralnetwork.compute;
import net.gonnot.neuralnetwork.matrix.Matrix;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static net.gonnot.neuralnetwork.matrix.MatrixLoaderTest.toMatrix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@RunWith(Enclosed.class)
public class ComputerTest {

    public static class BasicUseCase {
        @Test
        public void testMergeTopBottom() throws Exception {
            Matrix top =
                  Matrix.matrix(new double[][]{{1.1, 1.2}});
            Matrix bottom =
                  Matrix.matrix(new double[][]{{2.1, 2.2}});

            Matrix result = Computer.mergeTopBottom(top, bottom);

            assertThat(toMatrix(result)).containsExactly(new double[]{1.1, 1.2},
                                                         new double[]{2.1, 2.2});
        }


        @Test(expected = IllegalArgumentException.class)
        public void testMergeTopBottom_notSameColumnCount() throws Exception {
            Computer.mergeTopBottom(Matrix.matrix(new double[][]{{1.1, 1.2, 1.3},}),
                                    Matrix.matrix(new double[][]{{2.1, 2.2}}));
        }


        @Test
        public void testToStringEmptyMatrix() throws Exception {
            Matrix ones = Matrix.vector(new double[0]);
            assertThat(Computer.toString(ones)).isEqualTo("empty Matrix");
        }


        @Test
        public void testToStringVector() throws Exception {
            Matrix matrixA = Matrix.vector(new double[]{1, 2});

            assertThat(Computer.toString(matrixA)).isEqualTo("    1.0\n"
                                                             + "    2.0\n");
        }


        @Test
        public void testToStringMatrix() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1, 3, 2},
                        {4, 0, 1}
                  });
            assertThat(Computer.toString(matrixA)).isEqualTo("    1.0    3.0    2.0\n"
                                                             + "    4.0    0.0    1.0\n");
        }
    }

    public static class MatrixOperationsUseCase {
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


        @Test
        public void testMatrixMultiplyByVector() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1, 3, 2},
                        {4, 0, 1}
                  });
            Matrix matrixB = Matrix.matrix(
                  new double[][]{
                        {1},
                        {0},
                        {5}
                  });

            Matrix result = Computer.multiply(matrixA, matrixB);

            assertThat(toMatrix(result)).containsExactly(new double[]{11},
                                                         new double[]{9});
        }


        @Test
        public void testVectorMinusVector() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {10},
                        {20}
                  });
            Matrix matrixB = Matrix.matrix(
                  new double[][]{
                        {6},
                        {11}
                  });

            Matrix result = Computer.minus(matrixA, matrixB);

            assertThat(toMatrix(result)).containsExactly(new double[]{4},
                                                         new double[]{9});
        }


        @Test
        public void testMatrixMinusMatrix() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {10, 100},
                        {20, 200}
                  });
            Matrix matrixB = Matrix.matrix(
                  new double[][]{
                        {6, 60},
                        {11, 110}
                  });

            Matrix result = Computer.minus(matrixA, matrixB);

            assertThat(toMatrix(result)).containsExactly(new double[]{4, 40},
                                                         new double[]{9, 90});
        }


        @Test
        public void testMatrixMultiply() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1, 3, 2},
                        {4, 0, 1}
                  });
            Matrix matrixB = Matrix.matrix(
                  new double[][]{
                        {1, 3},
                        {0, 1},
                        {5, 2}
                  });

            Matrix result = Computer.multiply(matrixA, matrixB);

            assertThat(toMatrix(result)).containsExactly(new double[]{11, 10},
                                                         new double[]{9, 14});
        }
    }

    public static class SubMatrixUseCase {
        @Test
        public void testSubMatrix() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1.1, 1.2, 1.3},
                        {2.1, 2.2, 2.3}
                  });

            Matrix result = Computer.subMatrix(matrixA).rows(2).columns(2);

            assertThat(toMatrix(result)).containsExactly(new double[]{1.1, 1.2},
                                                         new double[]{2.1, 2.2});
        }


        @Test
        public void testSubMatrixWithSomeColumns() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1.1, 1.2, 1.3},
                        {2.1, 2.2, 2.3}
                  });

            Matrix result = Computer.subMatrix(matrixA)
                  .allRows()
                  .columns(2, 3);

            assertThat(toMatrix(result)).containsExactly(new double[]{1.2, 1.3},
                                                         new double[]{2.2, 2.3});
        }


        @Test
        public void testSubMatrixWithAllColumns() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1.1, 1.2, 1.3},
                        {2.1, 2.2, 2.3}
                  });

            Matrix result = Computer.subMatrix(matrixA).rows(1).allColumns();

            assertThat(toMatrix(result)).containsExactly(new double[]{1.1, 1.2, 1.3});
        }


        @Test
        public void testSubMatrixWithAllRows() throws Exception {
            Matrix matrixA = Matrix.matrix(
                  new double[][]{
                        {1.1, 1.2, 1.3},
                        {2.1, 2.2, 2.3}
                  });

            Matrix result = Computer.subMatrix(matrixA).allRows().columns(1);

            assertThat(toMatrix(result)).containsExactly(new double[]{1.1},
                                                         new double[]{2.1});
        }

        @Test
        public void testSubMatrixIllegalColumnRange() throws Exception {
            Matrix matrixA = Matrix.matrix(new double[][]{{1.1},});

            try {
                Computer.subMatrix(matrixA).allRows().columns(2);
                fail("Should throw an error");
            }
            catch (IllegalArgumentException e) {
                assertThat(e).hasMessage("Column Range out of bounds. Specified [1..2] but Matrix is [1..1]");
            }
            try {
                Computer.subMatrix(matrixA).allRows().columns(2,1);
                fail("Should throw an error");
            }
            catch (IllegalArgumentException e) {
                assertThat(e).hasMessage("Column Range out of bounds. Specified [2..1] but Matrix is [1..1]");
            }
        }

        @Test
        public void testSubMatrixIllegalRowsRange() throws Exception {
            Matrix matrixA = Matrix.matrix(new double[][]{{1.1},});

            try {
                Computer.subMatrix(matrixA).rows(2).allColumns();
                fail("Should throw an error");
            }
            catch (IllegalArgumentException e) {
                assertThat(e).hasMessage("Row Range out of bounds. Specified [1..2] but Matrix is [1..1]");
            }
            try {
                Computer.subMatrix(matrixA).rows(2,1).allColumns();
                fail("Should throw an error");
            }
            catch (IllegalArgumentException e) {
                assertThat(e).hasMessage("Row Range out of bounds. Specified [2..1] but Matrix is [1..1]");
            }
        }
    }
}

