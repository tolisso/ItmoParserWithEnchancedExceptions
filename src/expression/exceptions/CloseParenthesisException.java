package expression.exceptions;

public class CloseParenthesisException extends ParenthesisException {
    public CloseParenthesisException (String last, String cur) {
        super("Wrong parenthesis after \'" + last + "\': " + Replacer.replace(cur));
    }
}
