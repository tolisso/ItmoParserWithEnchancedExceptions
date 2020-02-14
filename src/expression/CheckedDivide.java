package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedDivide extends BinaryOperator {
    public CheckedDivide(Operator a, Operator b) {
        super(a, b);
        operator = " / ";
        priority = 5;
        isAss = false;
    }

    protected int doOperation(int first, int second) {
        boolean overflowFlag = false;
        if (first == Integer.MIN_VALUE && second == -1) {
            overflowFlag = true;
        }

        if (overflowFlag) {
            throw new OverflowException(first + "", operator, second + "");
        }
        int result = first / second;
        return result;
    }
    public double evaluate(double value) {
        return firstOperand.evaluate(value) / secondOperand.evaluate(value);
    }
}
