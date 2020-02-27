package expression.exceptions;

public class OperandException extends ParsingException {

    public OperandException(String operator, String operand, int pos) {
        super("No operand: " +
                operator + " " + Replacer.replace(operand) + " at position " + pos);
    }
    protected OperandException(String log) {
        super(log);
    }
}
