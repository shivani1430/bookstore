package com.bookstore.bookstore.validators;


import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Test;

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
}