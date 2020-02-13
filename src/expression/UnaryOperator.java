package expression;

public abstract class UnaryOperator extends Operator{
    protected Operator operand;

    public UnaryOperator (Operator operator) {
        this.operand = operator;
    }

    @Override
    public int getPriority() {
        return 10;
    }
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        toString(ans);
        return ans.toString();
    }
    @Override
    public String toMiniString() {
        StringBuilder ans = new StringBuilder();
        toMiniString(ans);
        return ans.toString();
    }
}
