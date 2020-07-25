package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.service.IBookService;
import com.bookstore.bookstore.service.IOrderService;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IBookService bookService;

    @MockBean
    private IOrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddBook() throws Exception {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        Book book = BookRequestUtil.getBookWithId();
        when(bookService.addBook(bookCreationRequest)).thenReturn(book);

        mvc.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(bookCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId())));
    }

    @Test
    public void testAddBookwithIsbnNull() throws Exception {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookCreationRequest.setIsbn(null);

        mvc.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(bookCreationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearch() throws Exception {
        BookSearchRequest bookSearchRequest = BookRequestUtil.getValidBookSearchRequest();
        Book book = BookRequestUtil.getBookWithId();
        when(bookService.search(bookSearchRequest)).thenReturn(Collections.singletonList(book));

        mvc.perform(post("/book/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(bookSearchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(1)));

    }

    @Test
    public void testSearchwithEmptyRequest() throws Exception {
        BookSearchRequest bookSearchRequest = new BookSearchRequest();

        mvc.perform(post("/book/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(bookSearchRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testSearchMedia() throws Exception {
        Book book = BookRequestUtil.getBookWithId();
        when(bookService.searchMedia("0987654321")).thenReturn(Collections.singletonList("book1"));

        mvc.perform(get("/book/media_search?isbn=0987654321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(1)));

    }

    @Test
    public void testSearchMediawithIsbnEmpty() throws Exception {

        mvc.perform(get("/book/media_search?isbn="))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        Order order = BookRequestUtil.getOrderWithId();
        when(orderService.createOrder(orderCreationRequest)).thenReturn(order);

        mvc.perform(post("/book/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(orderCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(order.getOrderId())));

    }
}