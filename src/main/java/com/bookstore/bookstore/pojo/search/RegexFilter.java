package com.bookstore.bookstore.pojo.search;

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
public class RegexFilter {
    private SearchField fieldName;
    private String token;
}

