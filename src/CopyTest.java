import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CopyTest extends SpeedTest {

    @Override
    public Collection<SpeedResult> run() {
        final var results = new LinkedList<SpeedResult>();

        final var bytes = TestData.repeat((byte)51, 600_000_000);

        for (int i = 0; i < 10; i++) {
            results.add(run("For-loop Copies Byte Array", () -> loopCopy(bytes), bytes.length));
            results.add(run("Arrays.copyOf Copies Byte Array", () -> arraysCopyOf(bytes), bytes.length));

            results.add(run("ByteBuffer.put Copies Byte Array", () -> byteBufferPut(bytes), bytes.length));
            results.add(run("ByteBuffer.wrap Copies Byte Array", () -> byteBufferWrap(bytes), bytes.length));
        }

        return results;
    }

    private static void loopCopy(final byte[] rawBytes)  {
        final var newBytes = new byte[rawBytes.length];

        for (var i = 0; i < rawBytes.length; i++) {
            newBytes[rawBytes.length - i - 1] = rawBytes[i];
        }
    }
    private static void arraysCopyOf(final byte[] bytes) {
        Arrays.copyOf(bytes, bytes.length);
    }

    private static void byteBufferPut(final byte[] bytes) {
        final var buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
    }
    private static void byteBufferWrap(final byte[] bytes) {
        ByteBuffer.wrap(bytes);
    }
}
