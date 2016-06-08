package net.gonnot.neuralnetwork.matrix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
/**
 *
 */
public class MatrixLoader {
    private MatrixLoader() {
    }


    public static Double[] load(Path path) throws IOException {
        try (Stream<String> stream = Files.lines(path)) {
            return streamToMatrix(stream);
        }
    }


    public static Double[] streamToMatrix(Stream<String> stream) {
        return stream
              .map(String::trim)
              .map(MatrixLoader::toDouble)
              .toArray(Double[]::new);
    }


    static double toDouble(String doubleInString) {
        return Double.parseDouble(doubleInString);
    }
}
