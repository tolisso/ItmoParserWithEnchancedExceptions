package expression.exceptions;

public class FirstOperandException extends OperandException{
//    "No first operand: " + lastParsed + " " + source.currentWord()
    public FirstOperandException(String last, String operator) {
        super("No first operand: " + last + " " + operator);
    }
}
