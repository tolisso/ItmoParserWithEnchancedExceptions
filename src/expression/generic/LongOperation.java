package expression.generic;

public class LongOperation implements Operation<Long> {

    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long divide(Long a, Long b) {
        return a / b;
    }

    @Override
    public Long negate(Long a) {
        return -a;
    }

    @Override
    public Long min(Long a, Long b) {
        return Math.min(a, b);
    }

    @Override
    public Long max(Long a, Long b) {
        return Math.max(a, b);
    }

    @Override
    public Long count(Long a) {
        return (long)Long.bitCount(a);
    }

    @Override
    public Long wrap(int a) {
        return (long)a;
    }
}
