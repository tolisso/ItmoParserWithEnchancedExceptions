package expression.exceptions;

public class ParsingException extends RuntimeException {
    protected ParsingException(String str) {
        super(str);
    }
}
