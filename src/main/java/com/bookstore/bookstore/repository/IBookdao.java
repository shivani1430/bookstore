package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;

/**
 * @author shivani_reddy
 */
public interface IBookdao {

    Book insert(Book book) throws DbException;

    Book save(Book book) throws DbException;

    Book get(String id) throws NotFoundException;
}
