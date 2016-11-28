package net.gonnot.neuralnetwork.exercise;
import java.io.IOException;
import java.nio.file.Paths;
import net.gonnot.neuralnetwork.graph.Grapher;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.MatrixLoader;
import net.gonnot.neuralnetwork.matrix.WritableMatrix;
import net.gonnot.neuralnetwork.operation.Operation;
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
public class Exercise1 {

    public static void main(String[] args) throws IOException {
        // Step - Load data

        Matrix data = MatrixLoader.load(Paths.get("data/exercise1/data.txt"));

        System.out.println(Operation.toString(data));

        Matrix X = Operation.subMatrix(data).allRows().columns(1, 1);
        Matrix y = Operation.subMatrix(data).allRows().columns(2, 2);

        // Step - Plot data

        Grapher.plotGraph()
              .title("Exercise 1")
              .seriesName("Market Size")
              .absciss("Population", X)
              .ordinate("Profit", y)
              .plot();

        // Step - Add bias

        int m = y.rows();
        X = Operation.mergeLeftRight(Matrix.ones(m, 1), X);
        Matrix theta = Matrix.zeros(2, 1);

        // Step - Compute Initial cost

        System.out.println("Initial cost");
        System.out.println("------------");
        double cost = computeCost(X, y, theta);
        System.out.println("  initial cost should be 32.07 = " + cost);

        // Step - Running gradient descent

        System.out.println("Training");
        System.out.println("--------");
        long begin = System.currentTimeMillis();
        int iterationCount = 20;
        double alpha = .01;
        WritableMatrix audit = WritableMatrix.matrix(iterationCount, 2);
        theta = gradientDescent(X, y, theta, alpha, iterationCount, audit);
        System.out.println(Operation.toString(theta));
        long end = System.currentTimeMillis();
        System.out.println("  Time --> " + ((end - begin) / 1000) + "s");

        // Step - Graph cost

        Matrix auditMatrix = audit.toMatrix();
        System.out.println("auditMatrix = " + Operation.toString(auditMatrix));
        Grapher.plotGraph()
              .title("Exercise 1 - alpha = " + alpha)
              .seriesName("Cost function")
              .absciss("Iteration", Operation.subMatrix(auditMatrix).allRows().columns(1, 1))
              .ordinate("cost", Operation.subMatrix(auditMatrix).allRows().columns(2, 2))
              .plot();

        // Step - Final cost

        System.out.println("Final cost");
        System.out.println("-----------");
        cost = computeCost(X, y, theta);
        System.out.println("  cost = " + cost);

        // Step - Prediction

        System.out.println("Prediction");
        System.out.println("----------");
        Matrix sample = Matrix.vector(new double[]{1, 11.7});
        Matrix prediction = Operation.multiply(Operation.transpose(sample), theta);
        System.out.println(" For population = 117000, we predict a profit of = " + (prediction.value(1, 1) * 10000));
    }


    static Matrix gradientDescent(Matrix x, Matrix y, Matrix theta, double alpha, int iterations, WritableMatrix audit) {
        int m = y.rows();

        double ratio = alpha / m;

        if (audit != null) {
            audit.setValue(1, 1, 1);
            audit.setValue(1, 2, computeCost(x, y, theta));
        }

        for (int i = 1; i < iterations; i++) {
            Matrix prediction = Operation.multiply(x, theta);
            Matrix errors = Operation.minus(prediction, y);

            theta = Operation.minus(theta, Operation.multiplyBy(ratio,
                                                                Operation.multiply(Operation.transpose(x), errors)));
            if (audit != null) {
                audit.setValue(i + 1, 1, i + 1);
                audit.setValue(i + 1, 2, computeCost(x, y, theta));
            }
        }

        return theta;
    }


    static double computeCost(Matrix x, Matrix y, Matrix theta) {
        int m = y.rows();

        Matrix prediction = Operation.multiply(x, theta);
        Matrix squareErrors = Operation.powerBy(2, Operation.minus(prediction, y));

        return 1. / (2. * m) * Operation.sum(squareErrors);
    }
}
