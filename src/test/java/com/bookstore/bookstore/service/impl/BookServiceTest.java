package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.Currency;
import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;
import com.bookstore.bookstore.service.IBookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author shivani_reddy
 */

@SpringBootTest
class BookServiceTest {

    @Autowired
    private IBookService bookService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addBook() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        BookCreationRequest bookCreationRequest = BookCreationRequest.builder()
                .isbn("0987654321")
                .title("earum")
                .author("kate night")
                .description("description1")
                .price(amount)
                .status(Status.ACTIVE)
                .build();
        try{
            Book book = bookService.addBook(bookCreationRequest);
          System.out.println(book);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateBook() {
        Amount amount = Amount.builder()
                .amount(20.0)
                .currency(Currency.INR)
                .build();
        BookUpdationRequest bookUpdationRequest = BookUpdationRequest.builder()
                .id("5f0d452951b6d6035b06b969")
                .title("Book1")
                .author("author2")
                .description("description2")
                .price(amount)
                .status(Status.ACTIVE)
                .version(1)
                .build();
        try{
            Book book = bookService.updateBook(bookUpdationRequest);
            System.out.println(book);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getBook() {
        try{
            Book book = bookService.getBook("5f0d452951b6d6035b06b969");
            System.out.println(book);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void searchMedia() {
        try{
            List<String> titles= bookService.searchMedia("0987654321");
            System.out.println("size of media posts : "+titles.size());
            System.out.println(titles);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void regex() {
        String para = "nisi error delectus possimus ut eligendi vitae placeat eos harum cupiditate facilis reprehenderit voluptatem beatae nmodi ducimus quo illum voluptas eligendi net nobis quia fugit";
        List<String> stringList = new ArrayList<>();
        stringList.add("Delectus possimus");
        stringList.add("FJAKFJDLF");
        String regex = String.format("(?i)(%s)", String.join("|",stringList));
        Pattern pt = Pattern.compile(regex);
        Matcher match = pt.matcher(para);
        System.out.println(match.find());
    }
}