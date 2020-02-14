package expression.exceptions;

public class ExpressionArithmeticException extends RuntimeException{
    protected ExpressionArithmeticException(String str) {
        super(str);
    }
}
