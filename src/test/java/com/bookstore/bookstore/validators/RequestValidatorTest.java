package com.bookstore.bookstore.validators;


import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author shivani_reddy
 */
public class RequestValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBookCreationRequestwithIsbnNull() {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookCreationRequest.setIsbn(null);
        RequestValidator.validate(bookCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookCreationRequestwithTitleEmpty() {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookCreationRequest.setTitle("  ");
        RequestValidator.validate(bookCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookCreationRequestwithPriceNull() {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookCreationRequest.setPrice(null);
        RequestValidator.validate(bookCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookCreationRequestwithCurrencyEmpty() {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookCreationRequest.getPrice().setCurrency(null);
        RequestValidator.validate(bookCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookSearchRequestwithAllEmpty() {
        BookSearchRequest bookSearchRequest = BookSearchRequest.builder().build();
        RequestValidator.validate(bookSearchRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderCreationRequestwithUserNull() {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderCreationRequest.setUserId(null);
        RequestValidator.validate(orderCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderCreationRequestwithUserEmpty() {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderCreationRequest.setUserId(" ");
        RequestValidator.validate(orderCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderCreationRequestwithOrderItemsEmpty() {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderCreationRequest.setOrderItems(new ArrayList<>());
        RequestValidator.validate(orderCreationRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderCreationRequestwithOrderItemsNull() {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderCreationRequest.setOrderItems(null);
        RequestValidator.validate(orderCreationRequest);
    }
}