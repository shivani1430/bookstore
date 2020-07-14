package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.BookCreationRequest;
import com.bookstore.bookstore.pojo.BookUpdationRequest;
import com.bookstore.bookstore.repository.IBookdao;
import com.bookstore.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shivani_reddy
 */

@Service
public class BookService implements IBookService {

    @Autowired
    IBookdao bookdao;

    @Override
    public Book addBook(BookCreationRequest bookCreationRequest) throws DbException {
        Book book = Book.builder()
                .isbn(bookCreationRequest.getIsbn())
                .title(bookCreationRequest.getTitle())
                .author(bookCreationRequest.getAuthor())
                .description(bookCreationRequest.getDescription())
                .price(bookCreationRequest.getPrice())
                .status(bookCreationRequest.getStatus())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return bookdao.insert(book);
    }

    @Override
    public Book updateBook(BookUpdationRequest bookUpdationRequest) throws DbException, NotFoundException {
        Book book = getBook(bookUpdationRequest.getId());
        book.setTitle(bookUpdationRequest.getTitle());
        book.setAuthor(bookUpdationRequest.getAuthor());
        book.setDescription(bookUpdationRequest.getDescription());
        book.setPrice(bookUpdationRequest.getPrice());
        book.setStatus(bookUpdationRequest.getStatus());
        book.setUpdatedAt(new Date());
        book.setVersion(bookUpdationRequest.getVersion());
        return bookdao.save(book);
    }

    @Override
    public Book getBook(String id) throws NotFoundException {
        return bookdao.get(id);
    }
}
