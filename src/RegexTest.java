import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class RegexTest extends SpeedTest {
    @Override
    public Collection<SpeedResult> run() {
        final var results = new LinkedList<SpeedResult>();

        for (int i = 0; i < 10; i++)
        {
            final var text = getText(i);

            final var bytes = text.getBytes(StandardCharsets.UTF_8);

            results.add(run("Regex Matches", () -> {
                final var pattern = Pattern.compile("Message(s)?(\\d+)");
                final var matcher = pattern.matcher(text);

                while (matcher.find()) ;
            }, bytes.length));
        }

        return results;
    }

    private static String getText(final int index)
    {
        final int count = 3_000_000;

        final var builder = new StringBuilder("Messages" + count);
        for (var i = 0; i < count; i++)
        {
            builder.append(" ");
            builder.append(index);
            builder.append(" Message");
            builder.append(i);
        }

        return builder.toString();
    }
}
