package expression.exceptions;

public class Replacer {
    public static String replace(String str) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '#') {
                ans.append("'end'");
            } else {
                ans.append(str.charAt(i));
            }
        }
        return ans + "";
    }
}
