package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.BookCreationRequest;
import com.bookstore.bookstore.pojo.BookSearchRequest;
import com.bookstore.bookstore.pojo.BookUpdationRequest;

import java.util.List;

/**
 * @author shivani_reddy
 */
public interface IBookService {

    Book addBook(BookCreationRequest bookCreationRequest) throws DbException;
    Book updateBook(BookUpdationRequest bookUpdationRequest) throws DbException, NotFoundException;
    Book getBook(String id) throws NotFoundException;
    List<Book> search(BookSearchRequest bookSearchRequest);
    List<String> searchMedia(String isbn);
}
