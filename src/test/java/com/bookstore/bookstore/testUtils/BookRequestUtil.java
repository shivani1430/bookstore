package com.bookstore.bookstore.testUtils;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.*;
import com.bookstore.bookstore.pojo.MediaPost;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author shivani_reddy
 */

public class BookRequestUtil {

    public static BookCreationRequest getValidBookCreationRequest() {
        Amount amount = Amount.builder()
                .currency(Currency.INR)
                .amount(20.0)
                .build();
        return BookCreationRequest.builder()
                .isbn("0987654321")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .stock(10)
                .status(Status.ACTIVE)
                .build();
    }

    public static BookUpdationRequest getValidBookUpdationRequest() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        return BookUpdationRequest.builder()
                .id("5f0d452951b6d6035b06b969")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .status(Status.ACTIVE)
                .stockToAdd(2)
                .build();
    }

    public static BookSearchRequest getValidBookSearchRequest() {
        return BookSearchRequest.builder()
                .isbn("0987654321")
                .build();
    }

    public static Book getBookWithId() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        Stock stock = Stock.builder()
                .stockTotal(20)
                .stockAvailable(20)
                .build();
        return Book.builder()
                .id("5f0d452951b6d6035b06b969")
                .isbn("0987654321")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .stock(stock)
                .status(Status.ACTIVE)
                .inventoryTransactionList(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .version(1)
                .build();
    }

    public static List<MediaPost> getMediaPosts() {
        List<MediaPost> mediaPosts = new ArrayList<>();
        mediaPosts.add(MediaPost.builder()
                .build());
        mediaPosts.add(MediaPost.builder()
                .title("book1")
                .build());
        mediaPosts.add(MediaPost.builder()
                .body("book1")
                .build());
        mediaPosts.add(MediaPost.builder()
                .title("book")
                .build());
        mediaPosts.add(MediaPost.builder()
                .title("Book1")
                .body("nisi error delectus possimus ut eligendi vitae placeat eos harum cupiditate")
                .build());
        return mediaPosts;
    }

    public static OrderCreationRequest getValidOrderCreationRequest() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .bookId("5f0d452951b6d6035b06b969")
                .quantity(1)
                .build();
        return OrderCreationRequest.builder()
                .userId("userid")
                .orderItems(Collections.singletonList(orderItem))
                .paymentType("cash")
                .totalAmount(amount)
                .build();
    }

    public static Order getOrderWithId() {
        OrderItem orderItem = OrderItem.builder()
                .bookId("5f0d452951b6d6035b06b969")
                .quantity(1)
                .build();
        Amount amount = Amount.builder()
                .currency(Currency.INR)
                .amount(20.0)
                .build();
        return Order.builder()
                .orderId("5f0d9bb6ce579865d2155756")
                .userId("userid")
                .orderItems(Collections.singletonList(orderItem))
                .orderStatus(OrderStatus.Confirmed)
                .paymentType("cash")
                .totalAmount(amount)
                .version(1)
                .build();

    }
}
