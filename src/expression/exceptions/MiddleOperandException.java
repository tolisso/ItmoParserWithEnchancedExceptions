package expression.exceptions;

public class MiddleOperandException extends OperandException{
//    "No first operand: " + lastParsed + " " + source.currentWord()
    public MiddleOperandException(String last, String operator) {
        super("No middle operand: " + last + " " + operator);
    }
}
