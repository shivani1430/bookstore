package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.pojo.OrderItem;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;

import java.util.List;

/**
 * @author shivani_reddy
 */
public interface IBookService {

    Book addBook(BookCreationRequest bookCreationRequest) throws DbException;

    Book updateBook(BookUpdationRequest bookUpdationRequest) throws DbException, NotFoundException;

    Book getBook(String id) throws NotFoundException;

    List<Book> bulkGet(List<String> ids) throws DbException;

    List<Book> search(BookSearchRequest bookSearchRequest) throws DbException;

    List<String> searchMedia(String isbn) throws Exception;
}
