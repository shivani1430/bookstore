package com.bookstore.bookstore.pojo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shivani_reddy
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private List<StringValueFilter> stringValueFilters;
    private List<RegexFilter> regexFilters;
}
