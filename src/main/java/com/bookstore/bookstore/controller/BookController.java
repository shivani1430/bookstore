package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.BaseResponse;
import com.bookstore.bookstore.pojo.BookCreationRequest;
import com.bookstore.bookstore.pojo.BookSearchRequest;
import com.bookstore.bookstore.service.IBookService;
import com.bookstore.bookstore.validators.BookRequestValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shivani_reddy
 */

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    IBookService bookService;

    @Autowired
    BookRequestValidator inputValidator;

    @PostMapping(value = "/add")
    public BaseResponse addBook(@RequestBody BookCreationRequest bookCreationRequest) {
        log.info("BookController:addBook - request : {}", bookCreationRequest);
        try {
            inputValidator.validate(bookCreationRequest);
            Book book = bookService.addBook(bookCreationRequest);
            return new BaseResponse(book);
        } catch (IllegalArgumentException ex) {
            log.error("BookController:addBook - IllegalArgumentException while adding book : {}, exception : {} ", bookCreationRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("BookController:addBook - Exception while adding book : {}, exception : {} ", bookCreationRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/search")
    public BaseResponse search(@RequestBody BookSearchRequest bookSearchRequest) {
        log.info("BookController:search - request : {}", bookSearchRequest);
        try {
            inputValidator.validate(bookSearchRequest);
            List<Book> books = bookService.search(bookSearchRequest);
            return new BaseResponse(books);
        } catch (IllegalArgumentException ex) {
            log.error("BookController:search - IllegalArgumentException while searching books : {}, exception : {} ", bookSearchRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("BookController:search - Exception while searching books : {}, exception : {} ", bookSearchRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
