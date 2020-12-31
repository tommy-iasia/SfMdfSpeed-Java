import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;

public class Utf8Test extends SpeedTest {

    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        final var fromText = TestData.repeat("I am very long very long very long very long...", 1_000_000);

        for (int i = 0; i < 10; i++) {
            final var pair = run(() -> fromText.getBytes(StandardCharsets.UTF_8));
            final var encodeTime = pair.getA();
            final var bytes = pair.getB();

            results.add(new SpeedResult("UTF8 Encodes Text", bytes.length, encodeTime));

            results.add(run("UTF8 Decodes Text", () -> new String(bytes, StandardCharsets.UTF_8), bytes.length));
        }

        return results;
    }
}
