package expression.exceptions;

public class BigDecimalException extends ParsingException {
    public BigDecimalException(String val) {
        super(val + " is not an int");
    }
}
