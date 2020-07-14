package com.bookstore.bookstore.validators;

import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.BookCreationRequest;
import com.bookstore.bookstore.pojo.BookSearchRequest;
import com.bookstore.bookstore.pojo.OrderCreationDocument;
import com.bookstore.bookstore.utils.GenericUtils;
import org.springframework.stereotype.Component;

/**
 * @author shivani_reddy
 */

@Component
public class BookRequestValidator {

    public void validate(BookCreationRequest request) {
        if (GenericUtils.isStringEmpty(request.getIsbn())
                || GenericUtils.isStringEmpty(request.getTitle())) {
            throw new IllegalArgumentException("isbn or title is missing");
        }
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

    public void validate(BookSearchRequest bookSearchRequest) {
        if (GenericUtils.isStringEmpty(bookSearchRequest.getIsbn())
                && GenericUtils.isStringEmpty(bookSearchRequest.getAuthor())
                && GenericUtils.isStringEmpty(bookSearchRequest.getTitle())) {
            throw new IllegalArgumentException("Missing token");
        }
    }

    public void validate(OrderCreationDocument orderCreationDocument) {
    }

}
