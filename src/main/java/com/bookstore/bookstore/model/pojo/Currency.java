package com.bookstore.bookstore.model.pojo;

/**
 * @author shivani_reddy
 */


public enum Currency {

    INR(0);

    private final int value;

    private Currency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
