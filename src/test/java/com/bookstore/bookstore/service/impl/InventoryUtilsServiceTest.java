package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
public class InventoryUtilsServiceTest {

    @Test
    public void checkAvailability() throws InventoryNotAvailable {
        Book book = BookRequestUtil.getBookWithId();
        boolean availability = InventoryUtilsService.checkAvailability(20, book);
        Assert.assertTrue(availability);
        InventoryUtilsService.checkAvailability(22, book);
        Assert.assertEquals(book.getStock().getStockAvailable(), 22);
        Assert.assertTrue(book.getStock().getStockTotal() >= 22);
    }
}