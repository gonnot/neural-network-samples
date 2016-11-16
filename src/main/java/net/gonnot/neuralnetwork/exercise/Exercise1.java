package net.gonnot.neuralnetwork.exercise;
import java.io.IOException;
import java.nio.file.Paths;
import net.gonnot.neuralnetwork.graph.Grapher;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.MatrixLoader;
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
        Matrix data = MatrixLoader.load(Paths.get("data/exercise1/data.txt"));

        System.out.println(Operation.toString(data));

        Matrix X = Operation.subMatrix(data).allRows().columns(1, 1);
        Matrix y = Operation.subMatrix(data).allRows().columns(2, 2);

        // Step - Plot Graph
        Grapher.plotGraph()
              .title("Exercise 1")
              .seriesName("Market Size")
              .absciss("Population", X)
              .ordinate("Profit", y)
              .plot();

        int m = y.rows();

        // Step - Running gradient descent

        X = Operation.mergeLeftRight(Matrix.ones(m, 1), X);
        Matrix theta = Matrix.zeros(2, 1);

        int iterations = 1500;
        double alpha = 0.01;

        double cost = computeCost(X, y, theta);

        System.out.println("cost = " + cost);
    }


    private static double computeCost(Matrix x, Matrix y, Matrix theta) {
        Matrix prediction = Operation.multiply(x, theta);
/*
        predictions = X*theta;
        sqrErrors = (predictions - y) .^2;


        J = 1/(2*m) * sum(sqrErrors);
*/

        return 0;
    }
}
