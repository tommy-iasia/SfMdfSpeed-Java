public class Pair<A, B> {
    public Pair(final A a, final B b) {
        this.a = a;
        this.b = b;
    }

    private final A a;
    public A getA() { return a; }

    private final B b;
    public B getB() { return b; }
}
