package expression;

import expression.generic.Operation;

public class CheckedNegate<T> extends UnaryOperator<T> {

    public CheckedNegate(Operator operator, Operation<T> operation) {
        super(operator, operation);
    }

    @Override
    protected T doOperation(T result) {
        return operation.negate(result);
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("-");
        sb.append("(");
        operand.toMiniString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("-");
        if (operand instanceof UnaryOperator || operand instanceof Const
                || operand instanceof Variable) {
            operand.toMiniString(sb);
        } else {
            sb.append("(");
            operand.toMiniString(sb);
            sb.append(")");
        }
    }

    @Override
    public String toMiniString() {
        StringBuilder ans = new StringBuilder();
        toMiniString(ans);
        return ans.toString();
    }
}
