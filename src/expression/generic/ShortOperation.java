package expression.generic;

public class ShortOperation implements Operation<Short> {

    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short divide(Short a, Short b) {
        return (short) (a / b);
    }

    @Override
    public Short negate(Short a) {
        return (short) (-a);
    }

    @Override
    public Short min(Short a, Short b) {
        return (short) (Math.min(a, b));
    }

    @Override
    public Short max(Short a, Short b) {
        return (short) (Math.max(a, b));
    }

    @Override
    public Short count(Short a) {
        short count = 0;
        for (int i = 0; i < 16; i++) {
            count+= (a & (1 << i)) == 0 ? 0 : 1;
        }
        return count;
    }

    @Override
    public Short wrap(int a) {
        return (short)a;
    }
}
