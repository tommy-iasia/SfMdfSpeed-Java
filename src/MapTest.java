import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapTest extends SpeedTest {

    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        for (int i = 0; i < 10; i++) {
            final int count = 1_000_000;
            results.add(run("Integer Fills Hashmap", () -> integerFillMap(count), count * 4));
            results.add(run("Integer To Hashmap", () -> integerToMap(count), count * 4));
        }

        return results;
    }

    private static void integerFillMap(final int count) {
        final Map<Integer, Boolean> map = new HashMap<>();

        for (int i = 0; i < count; i++) {
            map.put(i, true);
        }
    }

    private static void integerToMap(final int count) {
        IntStream.range(0, count)
                .mapToObj(t -> t)
                .collect(Collectors.toMap(t -> t, t -> true));
    }
}
