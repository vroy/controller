package org.exploid.weeb.util;

/**
 * <code>StringUtils</code> provides convenient methods to modify strings.
 * 
 * @author vince
 */
public class StringUtils {

    /**
     * Returns a string where only the first letter is an uppercase.
     * @param string string to capitalize.
     * @return a new string with the capitalization applied.
     */
    public static String capitalize(String string) {
        string = string.toLowerCase();

        String firstChar = new String(new char[]{string.charAt(0)}).toUpperCase();

        string = string.substring(1, string.length());

        return firstChar + string;
    }
}
