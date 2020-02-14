package expression.exceptions;

import java.beans.Expression;

public class OverflowException extends ExpressionArithmeticException {
    public OverflowException(String first, String operation, String second) {
        super("Overflow in expression: " + first + operation + second);
    }
    public OverflowException(String operation, String operand) {
        this("", operation, operand);
    }
}
