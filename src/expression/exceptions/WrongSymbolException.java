package expression.exceptions;

public class WrongSymbolException extends ParsingException {
    public WrongSymbolException(String last, String symbol, int pos) {
        super("Wrong symbols after '" + last + "': " + symbol + " at position " + pos);
    }
}
