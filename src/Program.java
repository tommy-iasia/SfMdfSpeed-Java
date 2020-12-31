import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Hey here");

        final var uuid = UUID.randomUUID();

        final String startTimeText = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Start at " + startTimeText);
        append("/* start " + uuid + " at " + startTimeText + " */");

        for (final var test : getTests())
        {
            System.out.println("Run " + test);

            for (final var result : test.run())
            {
                System.out.println(result.getTitle() + ": "
                        + Math.round((double)result.getSize() / result.getTime() / 1024 / 1024) + "MB/s: "
                        + result.getSize() + "B/" + result.getTime() + "s");

                append("results.push({"
                        + "\"Title\":\"" + result.getTitle() + "\","
                        + "\"Size\":" + result.getSize() + ","
                        + "\"Time\":" + result.getTime() + "})");
            }
        }

        final String endTimeText = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("End at " + endTimeText);
        append("/* end " + uuid + " at " + endTimeText + " */");
    }
    private static Collection<SpeedTest> getTests() {
        return Arrays.asList(
                new LoopTest(),
                new CopyTest(),
                new RegexTest(),
                new SocketTest(),
                new FileTest(),
                new Utf8Test(),
                new Base64Test(),
                new MapTest(),
                new LifeCycleTest()
        );
    }

    private static void append(String line) throws IOException {
        try (final var writer = new FileWriter("results.js", true)) {
            writer.append(line);
            writer.append("\r\n");
        }
    }
}
