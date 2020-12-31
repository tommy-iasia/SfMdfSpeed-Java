public class TestException extends RuntimeException {
    public TestException() { }

    public TestException(final Exception cause) { super(cause); }
}
