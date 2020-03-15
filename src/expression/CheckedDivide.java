package expression;

import expression.generic.Operation;

public class CheckedDivide<T> extends BinaryOperator<T> {
    public CheckedDivide(Operator a, Operator b, Operation<T> operation) {
        super(a, b, operation);
        operator = " / ";
        priority = 5;
        isAss = false;
    }

    protected T doOperation(T first, T second) {
        return operation.divide(first, second);
    }
}
