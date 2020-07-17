package com.bookstore.bookstore.pojo.apiRequest;

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
public class BookSearchRequest {
    private String isbn;
    private String title;
    private String author;
}
