package com.bookstore.bookstore.model.pojo;

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
public class OrderItem {
    private String bookId;
    private int quantity;
}
