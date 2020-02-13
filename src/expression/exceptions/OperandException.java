package expression.exceptions;

public class OperandException extends ParsingException {

    public OperandException(String operator, String operand) {
        super("No operand: " +
                operator + " " + Replacer.replace(operand));
    }
    protected OperandException(String log) {
        super(log);
    }
}
