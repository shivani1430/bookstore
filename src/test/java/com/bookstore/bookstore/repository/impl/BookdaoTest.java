package com.bookstore.bookstore.repository.impl;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.BookSearchRequest;
import com.bookstore.bookstore.repository.IBookdao;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

/**
 * @author shivani_reddy
 */

@SpringBootTest
class BookdaoTest {

    @Autowired
    private IBookdao bookdao;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    void search() {
        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
                .isbn("0987654321")
                .author("ate")
                .title("book")
                .build();
        try {
            List<Book> books = bookdao.search(bookSearchRequest);
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