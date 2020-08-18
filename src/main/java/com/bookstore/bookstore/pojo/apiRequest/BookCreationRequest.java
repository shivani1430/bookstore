package com.bookstore.bookstore.pojo.apiRequest;

import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.Status;
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
public class BookCreationRequest {

    private String isbn;
    private String title;
    private String author;
    private String description;
    private Amount price;
    private int stock;
    private Status status;
}
