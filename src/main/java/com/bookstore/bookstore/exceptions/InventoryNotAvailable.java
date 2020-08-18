package com.bookstore.bookstore.exceptions;

/**
 * @author shivani_reddy
 */
public class InventoryNotAvailable extends Exception{
    public InventoryNotAvailable(String message) {
        super(message);
    }
}
