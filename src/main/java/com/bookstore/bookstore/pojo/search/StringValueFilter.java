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
public class StringValueFilter {

    private SearchField fieldName;
    private List<String> values;

}
