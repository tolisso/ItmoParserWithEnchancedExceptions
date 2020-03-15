package expression.generic;

import expression.TripleExpression;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

import java.text.ParseException;

public class GenericTabulator implements Tabulator {
    String expression;
    int x1, x2, y1, y2, z1, z2;

    @Override
    public Object[][][] tabulate(String mode, String expression,
                                 int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        this.expression = expression;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;

        if (mode.equals("i")) {
            return getTable(new IntegerOperation());
        } else if (mode.equals("d")) {
            return getTable(new DoubleOperation());
        } else if (mode.equals("bi")) {
            return getTable(new BigIntegerOperation());
        } else if (mode.equals("u")) {
            return getTable(new UncheckedIntegerOperation());
        } else if (mode.equals("l")) {
            return getTable(new LongOperation());
        } else if (mode.equals("s")) {
            return getTable(new ShortOperation());
        } else {
            throw new IllegalArgumentException(mode + " mode is not supporting");
        }

    }

    private <T> Object[][][] getTable(Operation<T> operation) throws ParsingException {

        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<T>(operation);
        TripleExpression<T> parsedExpression = parser.parse(expression);
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        table[x - x1][y - y1][z - z1] = parsedExpression.evaluate(
                                operation.wrap(x), operation.wrap(y), operation.wrap(z));
                    } catch (Exception exc) {
                        table[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return table;
    }
}
