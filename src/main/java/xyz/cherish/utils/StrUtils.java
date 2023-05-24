package xyz.cherish.utils;

public class StrUtils {
    public static String lowerFirst(String s) {
        char firstChar = s.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            StringBuilder builder = new StringBuilder(s);
            builder.setCharAt(0, Character.toLowerCase(firstChar));
            return builder.toString();
        }
        return s;
    }
}
