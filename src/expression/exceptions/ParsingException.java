package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String str) {
        super(Replacer.replace(str));
    }
}
