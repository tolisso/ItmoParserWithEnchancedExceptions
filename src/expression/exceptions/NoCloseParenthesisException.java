package expression.exceptions;

public class NoCloseParenthesisException extends ParenthesisException {
    public NoCloseParenthesisException() {
        super("No close parenthesis");
    }
}
