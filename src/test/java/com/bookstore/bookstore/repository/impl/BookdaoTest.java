package com.bookstore.bookstore.repository.impl;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.search.RegexFilter;
import com.bookstore.bookstore.pojo.search.SearchField;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import com.bookstore.bookstore.pojo.search.StringValueFilter;
import com.bookstore.bookstore.repository.Idao;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author shivani_reddy
 */

@SpringBootTest
class BookdaoTest {

    @Autowired
    private Idao<Book> bookdao;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    void search() {
        List<StringValueFilter> stringValueFilters = new ArrayList<>();
        stringValueFilters.add(new StringValueFilter(SearchField.ISBN, Collections.singletonList("0987654321")));
        List<RegexFilter> regexFilters = new ArrayList<>();
        regexFilters.add(new RegexFilter(SearchField.TITLE, "book"));
        regexFilters.add(new RegexFilter(SearchField.AUTHOR, "ate"));
        SearchRequest searchRequest = SearchRequest.builder()
                .stringValueFilters(stringValueFilters)
                .regexFilters(regexFilters)
                .build();
        try {
            List<Book> books = bookdao.search(searchRequest);
            System.out.println("output size: " + books.size());
            System.out.println(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void deleteAll() {
        mongoOperations.dropCollection("books");
        System.out.println("cleared collection");
    }
}