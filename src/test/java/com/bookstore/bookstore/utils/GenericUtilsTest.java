package com.bookstore.bookstore.utils;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author shivani_reddy
 */

public class GenericUtilsTest {

    @Test
    public void testIsStringEmpty() {
        String text = null;
        Assert.assertTrue(GenericUtils.isStringEmpty(text));
        text = "";
        Assert.assertTrue(GenericUtils.isStringEmpty(text));
        text = "  ";
        Assert.assertTrue(GenericUtils.isStringEmpty(text));
        text = "kjhgf  ";
        Assert.assertFalse(GenericUtils.isStringEmpty(text));
    }
}