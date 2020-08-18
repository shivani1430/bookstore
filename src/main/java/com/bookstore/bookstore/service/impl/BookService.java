package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.builders.SearchRequestBuilder;
import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.pojo.Stock;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;
import com.bookstore.bookstore.pojo.MediaPost;
import com.bookstore.bookstore.repository.Bookdao;
import com.bookstore.bookstore.service.IBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author shivani_reddy
 */

@Service
public class BookService implements IBookService {

    private Logger log = LogManager.getLogger(BookService.class);

    @Autowired
    private Bookdao bookdao;

    @Autowired
    private MediaPostService mediaPostService;

    @Override
    public Book addBook(BookCreationRequest bookCreationRequest) throws DbException {
        Stock stock = Stock.builder()
                .stockTotal(bookCreationRequest.getStock())
                .stockAvailable(bookCreationRequest.getStock())
                .build();
        Book book = Book.builder()
                .isbn(bookCreationRequest.getIsbn())
                .title(bookCreationRequest.getTitle())
                .author(bookCreationRequest.getAuthor())
                .description(bookCreationRequest.getDescription())
                .price(bookCreationRequest.getPrice())
                .stock(stock)
                .status(bookCreationRequest.getStatus())
                .inventoryTransactionList(new ArrayList<>())
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
        book.getStock().setStockTotal(
                book.getStock().getStockTotal() + bookUpdationRequest.getStockToAdd());
        book.getStock().setStockAvailable(
                book.getStock().getStockAvailable() + bookUpdationRequest.getStockToAdd());
        book.setUpdatedAt(new Date());
        return bookdao.save(book);
    }

    @Override
    public Book getBook(String id) throws NotFoundException {
        return bookdao.get(id);
    }

    @Override
    public List<Book> bulkGet(List<String> ids) throws DbException {
        return bookdao.bulkGet(ids);
    }

    @Override
    public List<Book> search(BookSearchRequest bookSearchRequest) throws DbException {
        return bookdao.search(SearchRequestBuilder.build(bookSearchRequest));
    }

    @Override
    public List<String> searchMedia(String isbn) throws Exception {
        List<Book> bookList = bookdao.search(SearchRequestBuilder.build(isbn));
        if (bookList.isEmpty()) {
            throw new IllegalArgumentException("invalid isbn");
        }
        List<MediaPost> mediaPosts = mediaPostService.getMediaPosts();
        List<String> bookTitles = bookList.stream()
                .map(Book::getTitle).collect(Collectors.toList());

        String regex = String.format("(?i)(%s)", String.join("|", bookTitles));
        Pattern pt = Pattern.compile(regex);

        return Optional.ofNullable(mediaPosts).orElseGet(Collections::emptyList).stream()
                .filter(mediaPost -> {
                    Matcher match = pt.matcher(mediaPost.getTitle() + " " + mediaPost.getBody());
                    return match.find();
                })
                .map(MediaPost::getTitle).collect(Collectors.toList());
    }

}
