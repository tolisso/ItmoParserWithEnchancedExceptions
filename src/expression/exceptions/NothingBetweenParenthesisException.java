package expression.exceptions;

public class NothingBetweenParenthesisException extends ParenthesisException {
    public NothingBetweenParenthesisException(int pos) {
        super("Nothing between '(' and ')'" + " at position " + pos);
    }
}
