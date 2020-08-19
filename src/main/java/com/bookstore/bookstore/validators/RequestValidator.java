package com.bookstore.bookstore.validators;

import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.utils.GenericUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author shivani_reddy
 */

public class RequestValidator {

    public static void validate(BookCreationRequest request) {
        if (GenericUtils.isStringEmpty(request.getIsbn())
                || GenericUtils.isStringEmpty(request.getTitle())) {
            throw new IllegalArgumentException("isbn or title is missing");
        }
        request.setTitle(request.getTitle().trim());
        if (request.getPrice() == null) {
            throw new IllegalArgumentException("price is missing");
        } else {
            if (request.getPrice().getCurrency() == null) {
                throw new IllegalArgumentException("currency is missing");
            }
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.ACTIVE);
        }
    }

    public static void validate(BookSearchRequest bookSearchRequest) {
        if (GenericUtils.isStringEmpty(bookSearchRequest.getIsbn())
                && GenericUtils.isStringEmpty(bookSearchRequest.getAuthor())
                && GenericUtils.isStringEmpty(bookSearchRequest.getTitle())) {
            throw new IllegalArgumentException("Missing token");
        }
    }

    public static void validate(OrderCreationRequest orderCreationRequest) {
        if (GenericUtils.isStringEmpty(orderCreationRequest.getUserId())) {
            throw new IllegalArgumentException("Missing userId");
        }
        if (CollectionUtils.isEmpty(orderCreationRequest.getOrderItems())) {
            throw new IllegalArgumentException("empty OrderItems");
        }
        if (orderCreationRequest.getTotalAmount() == null) {
            throw new IllegalArgumentException("price is missing");
        } else {
            if (orderCreationRequest.getTotalAmount().getCurrency() == null) {
                throw new IllegalArgumentException("currency is missing");
            }
        }
    }

}
