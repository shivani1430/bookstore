package com.bookstore.bookstore.utils;

/**
 * @author shivani_reddy
 */
public class GenericUtils {

    public static boolean isStringEmpty(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
