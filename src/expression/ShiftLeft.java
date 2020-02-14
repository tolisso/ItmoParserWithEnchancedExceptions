package expression;

import java.util.Map;

public class ShiftLeft extends BinaryOperator {

    public ShiftLeft(Operator first, Operator second) {
        super(first, second);
        operator = " << ";
        priority = 1;
    }

    @Override
    protected int doOperation(int first, int second) {
        return first << second;
    }

    @Override
    public double evaluate(double x) {
        throw new IllegalArgumentException("You can't use shifts with double");
    }
}
