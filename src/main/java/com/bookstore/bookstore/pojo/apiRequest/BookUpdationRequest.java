package com.bookstore.bookstore.pojo.apiRequest;

import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author shivani_reddy
 */

@Data
@Builder
@AllArgsConstructor
public class BookUpdationRequest {

    private String id;
    private String title;
    private String author;
    private String description;
    private Amount price;
    private int stockToAdd;
    private Status status;
}
