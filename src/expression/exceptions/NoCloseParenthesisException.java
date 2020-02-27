package expression.exceptions;

public class NoCloseParenthesisException extends ParenthesisException {
    public NoCloseParenthesisException(int pos) {
        super("No close parenthesis" + " at position " + pos);
    }
}
