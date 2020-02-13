package expression.exceptions;

public class OverflowException extends RuntimeException {
    public OverflowException(String trace) {
        super(trace);
    }
}
