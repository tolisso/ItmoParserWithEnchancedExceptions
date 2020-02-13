package expression;

import java.util.Map;

public class Variable extends Operator {
    public final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        if (!values.containsKey(var)) {
            throw new IllegalArgumentException("You didn't initialized variable \"" + var + "\"");
        }
        return values.get(var);
    }

    public double evaluate(double x) {
        return x;
    }

    @Override
    public String toMiniString() {
        return "" + var;
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append(var);
    }

    @Override
    public String toString() {
        return "" + var;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(var);
    }

    @Override
    public int getPriority() {
        return 10;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable sec = (Variable)obj;
            return var.equals(sec.var);
        }
        return false;
    }
}
