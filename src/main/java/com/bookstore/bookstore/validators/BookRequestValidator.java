package com.bookstore.bookstore.validators;

import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.BookCreationRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author shivani_reddy
 */

@Component
public class BookRequestValidator {

    public void validate(BookCreationRequest request) {
        if (request.getIsbn() == null || request.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("isbn is missing");
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
}
