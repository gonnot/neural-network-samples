package net.gonnot.neuralnetwork.matrix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
/**
 *
 */
public class MatrixLoader {
    private MatrixLoader() {
    }


    public static Matrix load(Path path) throws IOException {
        try (Stream<String> stream = Files.lines(path)) {
            return streamToMatrix(stream);
        }
    }


    public static Matrix streamToMatrix(Stream<String> stream) {
        AtomicInteger cols = new AtomicInteger();
        double[] values = stream
              .map(String::trim)
              .flatMap(splitColumns(cols))
              .mapToDouble(MatrixLoader::toDouble)
              .toArray();
        if (cols.get() == 1) {
            return Matrix.vector(values);
        }
        else {
            return Matrix.matrix(cols.get(), values);
        }
    }


    private static Function<String, Stream<? extends String>> splitColumns(AtomicInteger cols) {
        return row -> {
            String[] columns = row.split(",");
            cols.set(columns.length);
            return Stream.of(columns);
        };
    }


    static double toDouble(String doubleInString) {
        return Double.parseDouble(doubleInString);
    }
}
