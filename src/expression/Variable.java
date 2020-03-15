package expression;

import expression.generic.Operation;

import java.util.Map;

public class Variable<T> extends Operator<T> {
    public final String var;

    public Variable(String var, Operation<T> operation) {
        this.var = var;
        this.operation = operation;
    }

    @Override
    public T evaluate(Map<String, T> values) {
        if (!values.containsKey(var)) {
            throw new IllegalArgumentException("You didn't initialized variable \"" + var + "\"");
        }
        return values.get(var);
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
