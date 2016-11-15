package net.gonnot.neuralnetwork.exercise;
import java.io.IOException;
import java.nio.file.Paths;
import net.gonnot.neuralnetwork.compute.Computer;
import net.gonnot.neuralnetwork.graph.Grapher;
import net.gonnot.neuralnetwork.matrix.Matrix;
import net.gonnot.neuralnetwork.matrix.MatrixLoader;
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
        Matrix matrix = MatrixLoader.load(Paths.get("data/exercise1/data.txt"));

        System.out.println(Computer.toString(matrix));

        Grapher.plot(matrix);
    }
}
