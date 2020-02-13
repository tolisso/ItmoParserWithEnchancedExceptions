package expression.exceptions;

public class SecondOperandException extends OperandException{
//    "No first operand: " + lastParsed + " " + source.currentWord()
    public SecondOperandException(String prev, String last, String operator) {
        super("No second operand: " + prev +
                " " + last + " " + operator);
    }
}
