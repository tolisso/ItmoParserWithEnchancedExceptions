package expression;

import java.util.Map;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperator {

    public CheckedAdd(Operator a, Operator b) {
        super(a, b);
        operator = " + ";
        priority = 2;
    }

    protected int doOperation(int first, int second) {
        boolean overflowFlag = false;
        if (second < 0) { // f + s = max
            if (first < Integer.MIN_VALUE - second) {
                overflowFlag = true;
            }
        } else {
            if (first > Integer.MAX_VALUE - second) {
                overflowFlag = true;
            }
        }

        if (overflowFlag) {
            throw new OverflowException(first + "", operator, second + "");
        }
        return first + second;
    }
    @Override
    public double evaluate(double x) {
        return firstOperand.evaluate(x) + secondOperand.evaluate(x);
    }
}
