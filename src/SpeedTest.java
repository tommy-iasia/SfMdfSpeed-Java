import java.util.Collection;
import java.util.concurrent.Callable;

public abstract class SpeedTest {
    public abstract Collection<SpeedResult> run() throws Exception;

    protected static double run(final Runnable runnable) {
        final var startTime = System.nanoTime();

        runnable.run();

        final var endTime = System.nanoTime();
        return (double) (endTime - startTime) / 1_000_000_000;
    }
    protected static SpeedResult run(final String title, final Runnable runnable, final long size)  {
        final var time = run(runnable);

        return new SpeedResult(title, size, time);
    }

    protected static <V> Pair<Double, V> run(final Callable<V> callable) throws Exception {
        final var startTime = System.nanoTime();

        final var result = callable.call();

        final var endTime = System.nanoTime();
        final var time = (double) (endTime - startTime) / 1_000_000_000;

        return new Pair(time, result);
    }
}
