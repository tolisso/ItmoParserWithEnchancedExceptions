package expression.exceptions;

public class SecondOperandException extends OperandException{
//    "No first operand: " + lastParsed + " " + source.currentWord()
    public SecondOperandException(String prev, String last, String operator, int pos) {
        super("No second operand: " + prev +
                " " + last + " " + operator + " at position " + pos);
    }
}
