package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedSubtract extends BinaryOperator {
    public CheckedSubtract(Operator a, Operator b) {
        super(a, b);
        operator = " - ";
        priority = 2;
        isAss = false;
    }

    @Override
    protected int doOperation(int first, int second) {
        int result = first - second;
        boolean overflowFlag = false;
        if (second > 0) { // f + s = max
            if (first < Integer.MIN_VALUE + second) {
                overflowFlag = true;
            }
        } else {
            if (first > Integer.MAX_VALUE + second) {
                overflowFlag = true;
            }
        }

        if (overflowFlag) {
            throw new OverflowException(first + "", operator, second + "");
        }
        return result;
    }

    @Override
    public double evaluate(double value) {
        return firstOperand.evaluate(value) - secondOperand.evaluate(value);
    }
}
