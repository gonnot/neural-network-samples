package net.gonnot.neuralnetwork.exercise;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.nio.file.Paths;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
/*
  _      _                         _____                              _
 | |    (_)                       |  __ \                            (_)
 | |     _ _ __   ___  __ _ _ __  | |__) |___  __ _ _ __ ___  ___ ___ _  ___  _ __
 | |    | | '_ \ / _ \/ _` | '__| |  _  // _ \/ _` | '__/ _ \/ __/ __| |/ _ \| '_ \
 | |____| | | | |  __/ (_| | |    | | \ \  __/ (_| | | |  __/\__ \__ \ | (_) | | | |
 |______|_|_| |_|\___|\__,_|_|    |_|  \_\___|\__, |_|  \___||___/___/_|\___/|_| |_|
                                               __/ |
                                              |___/
 */
public class Exercise1Ejml {

    public static void main(String[] args) throws IOException {
        // Step - Load data

        //Replace "," by " "
        final DenseMatrix64F DATA = MatrixIO.loadCSV(String.valueOf(Paths.get("data/exercise1/dataEJML.txt")), 97, 2);
        //DATA.print();

        int m = DATA.numRows;


        final DenseMatrix64F tmpX = new DenseMatrix64F(m, 2);
        extractMatrixInto(DATA, 0, m, 0, 1, tmpX);
        SimpleMatrix X = new SimpleMatrix(addColumnWihOnes(tmpX));
        //X.print();

        final DenseMatrix64F tmpY = new DenseMatrix64F(m, 1);
        extractMatrixInto(DATA, 0, m, 1, 2, tmpY);
        final SimpleMatrix Y = new SimpleMatrix(tmpY);
        //Y.print();

        SimpleMatrix theta = new SimpleMatrix(2, 1);

        // Step - Compute Initial cost

        System.out.println("Initial cost");
        System.out.println("------------");
        double cost = computeCost(X, Y, theta);
        System.out.println("  initial cost should be 32.07 = " + cost);

        // Step - Running gradient descent

        System.out.println("Training");
        System.out.println("--------");
        long begin = System.currentTimeMillis();
        theta = gradientDescent(X, Y, theta, 0.01, 1500);
        theta.print();
        long end = System.currentTimeMillis();
        System.out.println("  Time --> " + ((end - begin) / 1000) + "s");
        if (end - begin < 1000) {
            System.out.println("  Time --> " + (end - begin) + "ms");
        }

        // Step - Final cost

        System.out.println("Final cost");
        System.out.println("-----------");
        cost = computeCost(X, Y, theta);
        System.out.println("  cost = " + cost);

        // Step - Prediction

        System.out.println("Prediction");
        System.out.println("----------");
        SimpleMatrix sample = new SimpleMatrix(new double[][]{{1, 11.7}});

        SimpleMatrix prediction = sample.mult(theta);
        System.out.println(" For population = 117000, we predict a profit of = " + prediction.get(0, 0) * 10000);


    }

    private static double computeCost(SimpleMatrix x, SimpleMatrix y, SimpleMatrix theta) {
        int m = y.numRows();

        final SimpleMatrix prediction = x.mult(theta);
        final SimpleMatrix tmpSquareErrors = prediction.minus(y);
        final SimpleMatrix squareErrors = tmpSquareErrors.elementPower(2);

        return 1. / (2. * m) * squareErrors.elementSum();
    }

    static DenseMatrix64F addColumnWihOnes(DenseMatrix64F DATA) {
        DenseMatrix64F DATA_WithOnes = new DenseMatrix64F(DATA.numRows, DATA.numCols + 1);

        CommonOps.insert(DATA, DATA_WithOnes, 0, 1);
        for (int i = 0; i < DATA_WithOnes.getNumRows(); i++) {
            DATA_WithOnes.set(i, 0, 1);
        }
        return DATA_WithOnes;
    }

    private static SimpleMatrix gradientDescent(SimpleMatrix x, SimpleMatrix y, SimpleMatrix theta, double alpha, int iterations) {
        int m = y.numRows();

        double ratio = alpha / m;

        for (int i = 0; i <= iterations; i++) {
            SimpleMatrix prediction = x.mult(theta);
            SimpleMatrix errors = prediction.minus(y);
            theta= theta.minus(x.transpose().mult(errors).scale(ratio));
        }

        return theta;
    }

    static void extractMatrixInto(DenseMatrix64F mat, int y0, int y1, int x0, int x1, DenseMatrix64F dst) {
        dst.reshape(y1 - y0, x1 - x0);
        CommonOps.extract(mat, y0, y1, x0, x1, dst, 0, 0);
    }
}
