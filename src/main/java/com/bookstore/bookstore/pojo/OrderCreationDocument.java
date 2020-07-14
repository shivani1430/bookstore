package com.bookstore.bookstore.pojo;

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
public class OrderCreationDocument {

    private User user;
    private String bookId;
    private String transactionId;
}
