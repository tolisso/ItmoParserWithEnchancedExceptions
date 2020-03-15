package expression;

import expression.generic.Operation;

public class Count<T> extends UnaryOperator<T> {

    public Count(Operator operator, Operation<T> operation) {
        super(operator, operation);
    }

    @Override
    protected T doOperation(T result) {
        return operation.count(result);
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("count");
        sb.append("(");
        operand.toMiniString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("count");
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
