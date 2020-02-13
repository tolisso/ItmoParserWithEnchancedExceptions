package expression;

import java.util.Map;

public class ShiftRight extends BinaryOperator {

    public ShiftRight(Operator first, Operator second) {
        super(first, second);
        operator = " >> ";
        priority = 1;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        return firstOperand.evaluate(values) >> secondOperand.evaluate(values);
    }

    @Override
    public double evaluate(double x) {
        throw new IllegalArgumentException("You can't use shifts with double");
    }
}