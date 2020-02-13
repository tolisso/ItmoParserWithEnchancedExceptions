package expression.exceptions;

public class WrongSymbolException extends ParsingException {
    public WrongSymbolException(String last, String symbol) {
        super("Wrong symbols after '" + last + "': " + symbol);
    }
}
