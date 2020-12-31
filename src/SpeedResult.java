public class SpeedResult {
    public SpeedResult(final String title, final long size, final double time) {
        this.title = title;
        this.size = size;
        this.time = time;
    }

    private final String title;
    public String getTitle() { return title; }

    private final long size;
    public long getSize() { return size; }

    private final double time;
    public double getTime() { return time; }
}
