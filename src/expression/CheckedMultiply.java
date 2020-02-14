package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedMultiply extends BinaryOperator {
    public CheckedMultiply(Operator a, Operator b) {
        super(a, b);
        operator = " * ";
        priority = 5;
    }

    protected int doOperation(int first, int second) {
        if (checkMultiply(first, second)) {
            throw new OverflowException(first + "", operator, second + "");
        }
        return first * second;
    }

    @Override
    public double evaluate(double value) {
        return firstOperand.evaluate(value) * secondOperand.evaluate(value);
    }

    static boolean checkMultiply(int first, int second) {
        if (first == Integer.MIN_VALUE && second == -1) {
            return true;
        } else if (first == -1 && second == Integer.MIN_VALUE) {
            return true;
        } else {
            if (second > 0) { // a * b < max   a < max / b
                if (Integer.MAX_VALUE / second < first ||
                        Integer.MIN_VALUE / second > first) {
                    return true;
                }
            } else if (second < 0 && second != -1) {
                if (Integer.MAX_VALUE / second > first ||
                        Integer.MIN_VALUE / second < first) {
                    return true;
                }
            }
        }
        return false;
    }
}
