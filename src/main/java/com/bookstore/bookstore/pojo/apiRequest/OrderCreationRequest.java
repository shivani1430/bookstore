package com.bookstore.bookstore.pojo.apiRequest;

import com.bookstore.bookstore.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shivani_reddy
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {

    private User user;
    private String bookId;
    private String transactionId;
}
