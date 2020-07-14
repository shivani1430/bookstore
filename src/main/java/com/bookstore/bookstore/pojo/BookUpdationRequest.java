package com.bookstore.bookstore.pojo;

import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.Status;
import lombok.Builder;
import lombok.Data;

/**
 * @author shivani_reddy
 */

@Data
@Builder
public class BookUpdationRequest {

    private String id;
    private String title;
    private String author;
    private String description;
    private Amount price;
    private Status status;
    private int version;
}
