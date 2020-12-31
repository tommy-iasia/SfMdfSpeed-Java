public class Box<T> {
    public Box() { }
    public Box(T value) { this.value = value; }

    private T value;
    public synchronized T getValue() { return value; }
    public synchronized void setValue(T value) { this.value = value; }
}
