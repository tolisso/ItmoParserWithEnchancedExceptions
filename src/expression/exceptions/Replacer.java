package expression.exceptions;

class Replacer {
    static String replace(String str) {
        if (str.equals("#")) {
            return "'end'";
        }
        return str;
    }
}
