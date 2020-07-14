package com.bookstore.bookstore.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author shivani_reddy
 */

@Data
@Builder
public class BookSearchRequest {
    private String isbn;
    private String title;
    private String author;
}
