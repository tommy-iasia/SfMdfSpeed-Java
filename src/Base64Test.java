import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Base64Test extends SpeedTest {

    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        final var fromBytes = TestData.repeat((byte)51, 600_000_000);

        for (int i = 0; i < 10; i++) {
            final var encodePair = run(() -> Base64.getEncoder().encode(fromBytes));
            final var encodeTime = encodePair.getA();
            final var encodedBytes = encodePair.getB();

            results.add(new SpeedResult("Base64 Encodes Text",
                    fromBytes.length,
                    encodeTime));

            results.add(run("Base64 Decodes Text",
                    () -> Base64.getDecoder().decode(encodedBytes),
                    encodedBytes.length));
        }

        return results;
    }
}
