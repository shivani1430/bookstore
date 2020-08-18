package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.apiResponse.BaseResponse;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.service.IBookService;
import com.bookstore.bookstore.service.IOrderService;
import com.bookstore.bookstore.utils.GenericUtils;
import com.bookstore.bookstore.validators.RequestValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shivani_reddy
 */

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    private IBookService bookService;

    @Autowired
    private IOrderService orderService;

    @PostMapping(value = "/add")
    public BaseResponse addBook(@RequestBody BookCreationRequest bookCreationRequest) {
        log.info("BookController:addBook - request : {}", bookCreationRequest);
        try {
            RequestValidator.validate(bookCreationRequest);
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
            RequestValidator.validate(bookSearchRequest);
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

    @GetMapping(value = "/media_search")
    public BaseResponse mediaSearch(@RequestParam(value = "isbn") final String isbn) {
        log.info("BookController:mediaSearch - isbn : {}", isbn);
        try {
            if (GenericUtils.isStringEmpty(isbn)) {
                throw new IllegalArgumentException("invalid isbn");
            }
            List<String> postTitles = bookService.searchMedia(isbn);
            return new BaseResponse(postTitles);
        } catch (IllegalArgumentException ex) {
            log.error("BookController:mediaSearch - IllegalArgumentException with isbn : {}, exception : {} ", isbn, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("BookController:mediaSearch - Exception with isbn : {}, exception : {} ", isbn, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/buy")
    public BaseResponse createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        log.info("BookController:createOrder - request : {}", orderCreationRequest);
        try {
            RequestValidator.validate(orderCreationRequest);
            Order order = orderService.createOrder(orderCreationRequest);
            order = orderService.storeCashTransaction(order.getOrderId(), orderCreationRequest.getTotalAmount());
            return new BaseResponse(order);
        } catch (IllegalArgumentException ex) {
            log.error("BookController:createOrder - IllegalArgumentException while adding order : {}, exception : {} ", orderCreationRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("BookController:createOrder - Exception while adding order : {}, exception : {} ", orderCreationRequest, ex.getMessage());
            return new BaseResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
