package expression;

import expression.generic.Operation;

public class CheckedAdd<T> extends BinaryOperator<T> {

    public CheckedAdd(Operator<T> a, Operator<T> b, Operation<T> operation) {
        super(a, b, operation);
        operator = " + ";
        priority = 2;
    }

    @Override
    protected T doOperation(T first, T second) {
        return operation.add(first, second);
    }
}
