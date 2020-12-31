import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestData {

    public static byte[] repeat(final byte value, final int size) {
        final var array = new byte[size];

        Arrays.fill(array, value);

        return array;
    }

    public static String repeat(final String text, final int count) {
        final var builder = new StringBuilder();

        for (var i = 0; i < count; i++) {
            builder.append(text);
        }

        return builder.toString();
    }

    public static <T> List<T> repeat(final Function<Integer, T> function, final int count) {
        return IntStream.range(0, count)
                .mapToObj(function::apply)
                .collect(Collectors.toList());
    }
}
