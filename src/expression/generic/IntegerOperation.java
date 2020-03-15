package expression.generic;

import expression.exceptions.OverflowException;

public class IntegerOperation implements Operation<Integer> {

    @Override
    public Integer add(Integer a, Integer b) {
        boolean overflowFlag = false;
        if (b < 0) { // f + s = max
            if (a < Integer.MIN_VALUE - b) {
                overflowFlag = true;
            }
        } else {
            if (a > Integer.MAX_VALUE - b) {
                overflowFlag = true;
            }
        }
        if (overflowFlag) {
            throw new OverflowException(a + "", "+", b + "");
        }
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        boolean overflowFlag = false;
        if (b > 0) { // f + s = max
            if (a < Integer.MIN_VALUE + b) {
                overflowFlag = true;
            }
        } else {
            if (a > Integer.MAX_VALUE + b) {
                overflowFlag = true;
            }
        }

        if (overflowFlag) {
            throw new OverflowException(a + "", "-", b + "");
        }
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if (checkMultiply(a, b)) {
            throw new OverflowException(a + "", "/", b + "");
        }
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        boolean overflowFlag = false;
        if (a == Integer.MIN_VALUE && b == -1) {
            overflowFlag = true;
        }

        if (overflowFlag) {
            throw new OverflowException(a + "", "/", b + "");
        }
        return a / b;
    }

    @Override
    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("-", a + "");
        }
        return -a;
    }

    @Override
    public Integer min(Integer a, Integer b) {
        return Math.min(a, b);
    }

    @Override
    public Integer max(Integer a, Integer b) {
        return Math.max(a, b);
    }

    @Override
    public Integer count(Integer a) {
        return Integer.bitCount(a);
    }

    @Override
    public Integer wrap(int a) {
        return a;
    }

    private static boolean checkMultiply(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            return true;
        } else if (a == -1 && b == Integer.MIN_VALUE) {
            return true;
        } else {
            if (b > 0) { // a * b < max   a < max / b
                if (Integer.MAX_VALUE / b < a ||
                        Integer.MIN_VALUE / b > a) {
                    return true;
                }
            } else if (b < 0 && b != -1) {
                if (Integer.MAX_VALUE / b > a ||
                        Integer.MIN_VALUE / b < a) {
                    return true;
                }
            }
        }
        return false;
    }
}
