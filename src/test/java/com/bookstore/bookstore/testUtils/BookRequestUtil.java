package com.bookstore.bookstore.testUtils;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.Currency;
import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.model.pojo.User;
import com.bookstore.bookstore.pojo.MediaPost;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookSearchRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;

import java.util.ArrayList;
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
                .version(1)
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
        return Book.builder()
                .id("5f0d452951b6d6035b06b969")
                .isbn("0987654321")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .version(1)
                .build();
    }

    public static Book getBook() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        return Book.builder()
                .isbn("0987654321")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
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
        User user = User.builder()
                .userId("userid")
                .email("shivani@gmail.com")
                .phoneNo("0987654321")
                .name("shivani")
                .build();
        return OrderCreationRequest.builder()
                .bookId("5f0d452951b6d6035b06b969")
                .transactionId("1234567890")
                .user(user)
                .build();
    }

    public static Order getOrderWithId() {
        User user = User.builder()
                .userId("userid")
                .email("shivani@gmail.com")
                .phoneNo("0987654321")
                .name("shivani")
                .build();
        return Order.builder()
                .orderId("5f0d9bb6ce579865d2155756")
                .user(user)
                .bookId("5f0d452951b6d6035b06b969")
                .transactionId("1234567890")
                .version(1)
                .build();

    }
}
