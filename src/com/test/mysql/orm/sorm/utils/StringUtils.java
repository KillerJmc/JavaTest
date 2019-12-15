package com.test.mysql.orm.sorm.utils;

/**
 * Package the regular operations of Strings.
 * @author Jmc
 */
public class StringUtils {
    /**
     * change the first char to upper case
     * @param str src String
     * @return the String whose first char is upper case
     */
    public static String firstChar2UpperCase(String str) {
        return str.toUpperCase().substring(0, 1) + str.substring(1);
    }
}
