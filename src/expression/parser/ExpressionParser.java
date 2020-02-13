package expression.parser;

import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements Parser {
    private Source source;
    //Expression exc;
    private int depth = 0;
    private boolean isLastBinaryOperator = false;
    private String lastParsed = "";
    private String prevParsed = "begin";
    private boolean currentOperationParsed = false;

    public Operator parse(String str) {
//        System.out.println(str);
        source = new Source(str);
        Operator ans = null;
        Operator prev = null;
        while (source.current() != '#') {
            ans = parseValue(0, ans, false);
            if (ans == prev) {
                throw new CloseParenthesisException(lastParsed, source.currentWord());
            }
            prev = ans;
        }
//        if (source.current() != '#') {
//            throw new RuntimeException("Unexpected symbol: " + source.fromPos());
//        }
//        System.out.println("(" + str + ")");
        return ans;
    }

    public Operator parseValue(final int priority, Operator ans, boolean negate) {
        char current;
        int counter = 0;
        do {
            Operator prev = ans;
            source.skipWhitespaces();
            current = source.current();
            // System.out.println(source.currentWord());
            // System.out.println(current + " " + priority);
            // System.out.println(lastParsed + " " + current);
            if (current == 'x' || current == 'y' || current == 'z') {
                isLastBinaryOperator = false;
                setLastParsed(current);
                source.nextWord();
                if (priority == 10) {
                    return new Variable(current + "");
                } else {
                    ans = new Variable(current + "");
                }
                continue;
            }

            if (current == '(') {
                setLastParsed(current);
                isLastBinaryOperator = false;
                source.nextWord();
                Operator oldAns = ans;
                do {
                    ans = parseValue(0, ans, false);
                    // isLastBinaryOperator = false;
                } while (source.current() != ')' && source.current() != '#');
                if (source.current() == '#') {
                    throw new NoCloseParenthesisException();
                }
                if (oldAns == ans) {
                    throw new NothingBetweenParenthesisException();
                }
                setLastParsed(source.current());
                source.nextWord();
                isLastBinaryOperator = false;
                source.skipWhitespaces();
                current = source.current();
                if (priority >= getWordPriority(source.currentWord())) {
                    return ans;
                } else {
                    return parseValue(priority, ans, false);
                }
            }

            if (source.current() == ')') {
                if (isLastBinaryOperator || negate) {
                    isLastBinaryOperator = false;
                    return null;
//                    throw new RuntimeException("wrong symbol after \'" + lastParsed + "\': " + source.current());
                }
                isLastBinaryOperator = false;
                return ans;
            }

            current = source.current();

            if ('0' <= current && current <= '9') {
                isLastBinaryOperator = false;
                Integer curInt;
                if (current != '0') {
                    curInt = parseInt(negate);
                } else {
                    curInt = 0;
                    source.next();
                }
                source.skipWhitespaces();
                source.parseCurrentWord();
                current = source.current();
                if (ans != null) {
                    throw new WrongSymbolException(lastParsed, curInt + "");
                }
                setLastParsed(curInt + "");
                if (getWordPriority(source.currentWord()) < priority) {
                    if (negate) {
                        return new Const(Integer.valueOf(curInt), true);
                    }
                    return new Const(curInt);
                } else {
                    if (negate) {
                        return parseValue(priority, new Const(Integer.valueOf(curInt), true), false);
                    }
                    return parseValue(priority, new Const(curInt), false);
                }
            }

            if (source.currentWord().equals("+") || source.currentWord().equals("-") ||
                    source.currentWord().equals("*") || source.currentWord().equals("/") ||
                    source.currentWord().equals("abs") || source.currentWord().equals("square") ||
                    source.currentWord().equals("<<") || source.currentWord().equals(">>")) {
                if (priority >= getWordPriority(source.currentWord()) && ans != null) {
                    return ans;
                }
                if (!source.currentWord().equals("-")) {
                    if (ans == null) {
                        if (isOperator(lastParsed)) {
                            throw new MiddleOperandException(lastParsed, source.currentWord());
                        } else {
                            throw new FirstOperandException(lastParsed, source.currentWord());
                        }
                    }
//                    if (/* isLastBinaryOperator || */negate) {
//                        throw new RuntimeException("wrong symbol " + source.currentWord());
//                    }
                    isLastBinaryOperator = true;
                }
                setLastParsed(source.currentWord());
                source.nextWord();
                current = source.current();

                if (lastParsed.equals("<<")) {

                    Operator secondOperand = parseValue(getPriority('<'), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new ShiftLeft(ans, secondOperand);
                    if (priority < getPriority('<')) {
                        ans = result;
                    } else {
                        return result;
                    }
                }

                if (lastParsed.equals(">>")) {
                    Operator secondOperand = parseValue(getPriority('>'), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new ShiftRight(ans, secondOperand);
                    if (priority < getPriority('>')) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("abs")) {
                    Operator operand = parseValue(10, null, false);
                    checkOperand(operand);
                    if (priority < 10) {
                        ans = new Abs(operand); // 10 is UnaryOperator priority

                    } else if (priority >= 10) {
                        return new Abs(operand);
                    }
                }
                if (lastParsed.equals("square")) {
                    Operator operand = parseValue(10, null, false);
                    checkOperand(operand);
                    if (priority < 10) {
                        ans = new Square(operand);
                    } else if (priority >= 10) {
                        return new Square(operand);
                    }
                }
                if (lastParsed.equals("+")) {

                    Operator secondOperand = parseValue(getPriority('+'), null, false);
                    if (secondOperand == null) {
                        throw new SecondOperandException(prevParsed, lastParsed, source.currentWord());
                    }
                    Operator result = new CheckedAdd(ans, secondOperand);

                    if (priority < getPriority('+')) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("-")) {
                    if (ans == null || isLastBinaryOperator) {
                        Operator res = parseValue(10, null, true);
                        if (res == null) {
                            throw new OperandException(lastParsed, source.currentWord());
                        }
                        if (priority < 10) {
                            ans = new CheckedNegate(res); // 10 is UnaryOperator priority
                            if (res instanceof Const) {
                                if (((Const) res).isNegated()) {
                                    ((Const) res).deleteNegate();
                                    ans = res;
                                } else {
                                    ans = new Const(-res.evaluate(0));
                                }
                            }
                        } else if (priority >= 10) {
                            if (res instanceof Const) {
                                if (((Const) res).isNegated()) {
                                    ((Const) res).deleteNegate();
                                    return res;
                                } else {
                                    return new Const(-res.evaluate(0));
                                }
                            }
                            return new CheckedNegate(res);
                        }
                    } else {
                        isLastBinaryOperator = true;
                        Operator secondOperand = parseValue(getPriority('-'), null, false);
                        if (secondOperand == null) {
                            throw new SecondOperandException(prevParsed, lastParsed, source.currentWord());
                        }
                        Operator result = new CheckedSubtract(ans, secondOperand);
                        if (priority < getPriority('-')) {
                            ans = result;
                        } else {
                            return result;
                        }
                    }
                }
                if (lastParsed.equals("*")) {
                    Operator secondOperand = parseValue(getPriority('*'), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new CheckedMultiply(ans, secondOperand);

                    if (priority < getPriority('*')) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("/")) {
                    Operator secondOperand = parseValue(getPriority('/'), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new CheckedDivide(ans, secondOperand);
                    if (priority < getPriority('/')) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
            }
            if (ans == prev) {
                if (source.current() == '#') {
                    return ans;
                }
                throw new WrongSymbolException(prevParsed, source.currentWord());
            }
        } while (true);
    }

    private int getPriority(char ch) {
        if (ch == '+') {
            return new CheckedAdd(null, null).getPriority();
        }
        if (ch == '-') {
            return new CheckedSubtract(null, null).getPriority();
        }
        if (ch == '*') {
            return new CheckedMultiply(null, null).getPriority();
        }
        if (ch == '/') {
            return new CheckedDivide(null, null).getPriority();
        }
        if (ch == '>') {
            return new ShiftRight(null, null).getPriority();
        }
        if (ch == '<') {
            return new ShiftLeft(null, null).getPriority();
        }
        if (ch == 'a') {
            return 10;
        }
        if (ch == 's') {
            return 10;
        }
        if (ch == ')') {
            return 10;
        }
        return 0;
    }
    private int getWordPriority(String word) {
        if (word.equals("+")) {
            return new CheckedAdd(null, null).getPriority();
        }
        if (word.equals("-")) {
            return new CheckedSubtract(null, null).getPriority();
        }
        if (word.equals("*")) {
            return new CheckedMultiply(null, null).getPriority();
        }
        if (word.equals("/")) {
            return new CheckedDivide(null, null).getPriority();
        }
        if (word.equals(">>")) {
            return new ShiftRight(null, null).getPriority();
        }
        if (word.equals("<<")) {
            return new ShiftLeft(null, null).getPriority();
        }
        if (word.equals("abs")) {
            return 10;
        }
        if (word.equals("square")) {
            return 10;
        }
        if (word.equals(")")) {
            return 10;
        }
        return 0;
    }

    private int parseInt(boolean negate) {
        StringBuilder sb = new StringBuilder(source.current());
        while ('0' <= source.current() && source.current() <= '9') {
            sb.append(source.current());
            source.next();
        }
        source.parseCurrentWord();
        try {
            if (negate) {
                return Integer.parseInt("-" + sb);
            } else {
                return Integer.parseInt(sb.toString());
            }
        } catch (NumberFormatException exc) {
            throw new OverflowException("Tried to convert " + (negate ? "-" : "") +  sb + " into int");
        }
    }

    void setLastParsed(String parsed) {
        prevParsed = lastParsed;
        lastParsed = parsed;
    }
    void setLastParsed(char parsed) {
        setLastParsed(parsed + "");
    }
    void checkSecondOperand(Operator secondOperand) {
        if (secondOperand == null) {
            throw new SecondOperandException(prevParsed, lastParsed, source.currentWord());
        }
    }
    void checkOperand(Operator operand) {
        if (operand == null) {
            throw new OperandException(lastParsed, source.currentWord());
        }
    }
    boolean isOperator(String str) {
        switch(str) {
            case "+":
            case "-":
            case "*":
            case "/":
            case ">>":
            case "<<":
                return true;
        }
        return false;
    }
}