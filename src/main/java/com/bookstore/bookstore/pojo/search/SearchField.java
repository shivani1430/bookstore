package com.bookstore.bookstore.pojo.search;

import lombok.AllArgsConstructor;

/**
 * @author shivani_reddy
 */

@AllArgsConstructor
public enum SearchField {

    ID("id"),

    ISBN("isbn"),

    TITLE("title"),

    AUTHOR("author");

    private String value;

    public String getValue() {
        return value;
    }
}
