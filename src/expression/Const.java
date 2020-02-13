package expression;

import java.util.Map;

public class Const extends Operator {
    public Integer value;
    public Double doubleValue;
    private boolean negate;
    public Const(Integer val) {
        value = val;
        doubleValue = (double)val;
    }
    public Const(Integer val, boolean negate) {
        this(val);
        this.negate = negate;
    }
    public Const(Double val) {
        doubleValue = val;
    }

    public boolean isNegated() {
        return negate;
    }
    public void deleteNegate() {
        negate = false;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        return value.intValue();
    }

    @Override
    public double evaluate(double x) {
        return doubleValue;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value + "";
        }
        return doubleValue + "";
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void toString(StringBuilder sb) {
        if (value != null) {
            sb.append(value);
        } else {
            sb.append(doubleValue.toString());
        }
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        toString(sb);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const sec = (Const)obj;
            if (value != null && sec.value != null) {
                return value.equals(sec.value);
            }
            if (value == null && sec.value == null) {
                return doubleValue.equals(sec.doubleValue);
            }
        }
        return false;
    }
}
