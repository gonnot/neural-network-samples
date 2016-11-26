package net.gonnot.neuralnetwork.exercise;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.MatrixFeatures;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class Exercise1EjmlTest {
    private DenseMatrix64F X;

    @Before
    public void setUp() {
        double[] data =
                {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
        X = DenseMatrix64F.wrap(3, 3, data);
    }


    @Test
    public void testReshape() {
        X.reshape(2, 2);
        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {1, 2},
                {3, 4}
        }), X));
    }


    @Test
    public void testCombineOnSimpleMatrix() {
        SimpleMatrix A = new SimpleMatrix();
        final SimpleMatrix B = new SimpleMatrix(new double[][]{
                {1},
                {1},
                {1}
        });
        final SimpleMatrix combine = A.wrap(X).combine(0, 0, B);


        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {1, 2, 3},
                {1, 5, 6},
                {1, 8, 9}}), combine.getMatrix()));

    }


    @Test
    public void testAddColumnWithOnes() {
        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {1, 1, 2, 3},
                {1, 4, 5, 6},
                {1, 7, 8, 9}}), Exercise1Ejml.addColumnWihOnes(X)));

    }

    @Test
    public void testExtractMatrixInfo() {
        DenseMatrix64F Y = new DenseMatrix64F(0);
        Exercise1Ejml.extractMatrixInto(X, 0, 2, 0, 2, Y);
        Y.print();
        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {1, 2},
                {4, 5},
        }), Y));

    }

    @Test
    public void testMultiply() {
        DenseMatrix64F Y = new DenseMatrix64F(3, 3);

        DenseMatrix64F theta = new DenseMatrix64F(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        CommonOps.mult(1, theta, X, Y);

        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {30, 36, 42},
                {66, 81, 96},
                {102, 126, 150}}), Y));

        Y = new DenseMatrix64F(1, 3);
        theta = new DenseMatrix64F(new double[][]{
                {1, 2, 3}
        });
        CommonOps.mult(1, theta, X, Y);
        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                        {30, 36, 42}}),
                Y));

    }


    @Test
    public void testElementSum() {
        assertThat(CommonOps.elementSum(X)).isEqualTo(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9);
    }


    @Test
    public void testElementPower() {
        SimpleMatrix mat = new SimpleMatrix(X);

        assertTrue(MatrixFeatures.isEquals(new DenseMatrix64F(new double[][]{
                {1, 4, 9},
                {16, 25, 36},
                {49, 64, 81}
        }), mat.elementPower(2).getMatrix()));
    }
}
