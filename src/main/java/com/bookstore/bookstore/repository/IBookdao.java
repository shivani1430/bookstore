package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.BookSearchRequest;

import java.util.List;

/**
 * @author shivani_reddy
 */
public interface IBookdao {

    Book insert(Book book) throws DbException;

    Book save(Book book) throws DbException;

    Book get(String id) throws NotFoundException;

    List<Book> search(BookSearchRequest bookSearchRequest);

    List<Book> getByIsbn(String id);
}
