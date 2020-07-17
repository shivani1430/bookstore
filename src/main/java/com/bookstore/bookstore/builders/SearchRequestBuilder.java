package com.bookstore.bookstore.builders;

import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.search.RegexFilter;
import com.bookstore.bookstore.pojo.search.SearchField;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import com.bookstore.bookstore.pojo.search.StringValueFilter;
import com.bookstore.bookstore.utils.GenericUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author shivani_reddy
 */
public class SearchRequestBuilder {

    public static SearchRequest build(BookSearchRequest bookSearchRequest) {
        List<StringValueFilter> stringValueFilterList = new ArrayList<>();
        List<RegexFilter> regexFilterList = new ArrayList<>();
        if (!GenericUtils.isStringEmpty(bookSearchRequest.getIsbn())) {
            stringValueFilterList.add(new StringValueFilter(SearchField.ISBN, Collections.singletonList(bookSearchRequest.getIsbn())));

        }
        if (!GenericUtils.isStringEmpty(bookSearchRequest.getTitle())) {
            regexFilterList.add(new RegexFilter(SearchField.TITLE, bookSearchRequest.getTitle()));

        }
        if (!GenericUtils.isStringEmpty(bookSearchRequest.getAuthor())) {
            regexFilterList.add(new RegexFilter(SearchField.AUTHOR, bookSearchRequest.getAuthor()));

        }
        return SearchRequest.builder()
                .stringValueFilters(stringValueFilterList)
                .regexFilters(regexFilterList)
                .build();
    }

    public static SearchRequest build(String isbn) {
        List<StringValueFilter> stringValueFilterList = new ArrayList<>();
        stringValueFilterList.add(new StringValueFilter(SearchField.ISBN, Collections.singletonList(isbn)));
        return SearchRequest.builder()
                .stringValueFilters(stringValueFilterList)
                .build();
    }


}
