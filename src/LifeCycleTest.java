import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LifeCycleTest extends SpeedTest {
    @Override
    public Collection<SpeedResult> run() throws Exception {
        final var results = new LinkedList<SpeedResult>();

        final var runtime = Runtime.getRuntime();

        for (int i = 0; i < 10; i++) {
            System.gc();

            final var box = new Box<List<Block>>();

            final var startMemory = runtime.totalMemory() - runtime.freeMemory();

            final double createTime = run(() -> {
                final var blocks = IntStream.range(0, 50_000_000)
                        .mapToObj(j -> new Block(j, j, j, j))
                        .collect(Collectors.toList());

                box.setValue(blocks);
            });
            final var beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            results.add(new SpeedResult("Create Objects", beforeMemory - startMemory, createTime));

            final double collectTime = run(() -> {
                box.setValue(null);

                System.gc();
            });
            final long afterMemory = runtime.totalMemory() - runtime.freeMemory();
            results.add(new SpeedResult("GC Removes Objects", beforeMemory - afterMemory, collectTime));
        }

        return results;
    }

    private class Block {
        public Block(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        private int a;
        private int b;
        private int c;
        private int d;

        @Override
        public String toString() {
            return a + ", " + b + ", " + c + ", " + d;
        }
    }
}
