package expression.exceptions;

public class CloseParenthesisException extends ParenthesisException {
    public CloseParenthesisException (String last, String cur, int pos) {
        super("Wrong parenthesis after \'" + last + "\': " + Replacer.replace(cur) + " at position " + pos);
    }
}
