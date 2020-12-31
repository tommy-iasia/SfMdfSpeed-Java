import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LoopTest extends SpeedTest {
    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        final int count = 600_000_000;
        final var bytes = TestData.repeat((byte)51, count);

        for (int i = 0; i < 10; i++) {
            results.add(run("For-loop Iterates", () -> forLoop(count), count));

            results.add(run("For-loop Reads Byte Array", () -> forLoop(bytes), bytes.length));
            results.add(run("For-each Reads Byte Array", () -> forEach(bytes), bytes.length));
        }

        return results;
    }

    private static void forLoop(final int count)  {
        for (var i = 0; i < count; i++) {
            var c = i + 1;
        }
    }

    private static void forLoop(final byte[] bytes)  {
        for (var i = 0; i < bytes.length; i++) {
            final var b = bytes[i];
            var c = b + 1;
        }
    }
    private static void forEach(final byte[] bytes)  {
        for (final var b : bytes) {
            var c = b + 1;
        }
    }
}
