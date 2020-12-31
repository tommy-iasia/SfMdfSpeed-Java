import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileTest extends SpeedTest {
    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        final String className = getClass().getSimpleName();
        final var paths = IntStream.range(0, 10)
                .mapToObj(i -> Paths.get(className + "-" + UUID.randomUUID().toString() + ".bin"))
                .collect(Collectors.toList());

        final var writeBytes = TestData.repeat((byte)51, 1_000_000_000);

        try {
            for (final Path path : paths)  {
                results.add(run("Writes File Bytes", () -> {
                    try {
                        Files.write(path, writeBytes, StandardOpenOption.CREATE);
                    } catch (final IOException e) {
                        throw new TestException(e);
                    }
                }, writeBytes.length));
            }

            for (final Path path : paths)  {
                results.add(run("Reads File Bytes", () -> {
                    try {
                        final var readBytes = Files.readAllBytes(path);

                        if (readBytes.length < writeBytes.length) {
                            throw new TestException();
                        }
                    } catch (final Exception e) {
                        throw new TestException(e);
                    }
                }, writeBytes.length));
            }
        }
        finally {
            for (final Path path : paths) {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
        }

        return results;
    }
}
