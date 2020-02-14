package expression;

import expression.exceptions.OverflowException;
import expression.exceptions.PowException;

import java.util.ArrayList;
import java.util.Map;

public class Pow extends BinaryOperator {
    public Pow(Operator a, Operator b) {
        super(a, b);
        operator = " ** ";
        priority = 7;
    }

    @Override
    protected int doOperation(int base, int power) {
        if (power < 0) {
            throw new PowException("pow with negative power: " + power);
        }
        if (power == 0) {
            if (base == 0) {
                throw new PowException("0**0 exception");
            }
            return 1;
        }
        int val = power;
        ArrayList<String> order = new ArrayList<>();
        while (val != 1) {
            if (val % 2 == 1) {
                order.add("a");
                val--;
            } else {
                order.add("a^n");
                val /= 2;
            }
        }
        int res = base;
        for (int i = order.size() - 1; i > -1; i--) {
            if (order.get(i).equals("a")) {
                if (CheckedMultiply.checkMultiply(res, base)) {
                    throw new OverflowException(base + "", operator, power + "");
                }
                res *= base;
            } else {
                if (CheckedMultiply.checkMultiply(res, res)) {
                    throw new OverflowException(base + "", operator, power + "");
                }
                res *= res;
            }
        }
        return res;
    }


    @Override
    public double evaluate(double value) {
        throw new IllegalArgumentException("Function is not realized :(");
    }
}
