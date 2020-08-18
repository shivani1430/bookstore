package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.model.Book;

/**
 * @author shivani_reddy
 */
public class InventoryUtilsService {

    public static boolean checkAvailability(int quantityToBlocked, Book book) throws InventoryNotAvailable {
        int stockAvailable = book.getStock().getStockAvailable();
        if (stockAvailable < quantityToBlocked) {
            int toAdd = quantityToBlocked - stockAvailable;
            book.getStock().setStockTotal(
                    book.getStock().getStockTotal() + toAdd);
            book.getStock().setStockAvailable(
                    stockAvailable + toAdd);
//            throw new InventoryNotAvailable("inventory not available");
        }
        return true;
    }
}
