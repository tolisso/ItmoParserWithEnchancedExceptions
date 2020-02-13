package expression.exceptions;

public class NothingBetweenParenthesisException extends ParenthesisException {
    public NothingBetweenParenthesisException() {
        super("Nothing between '(' and ')'");
    }
}
