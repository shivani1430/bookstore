package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.builders.SearchRequestBuilder;
import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.pojo.InventoryStatus;
import com.bookstore.bookstore.model.pojo.InventoryTransaction;
import com.bookstore.bookstore.pojo.apiRequest.BookCreationRequest;
import com.bookstore.bookstore.pojo.apiRequest.BookUpdationRequest;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import com.bookstore.bookstore.repository.Bookdao;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private Bookdao bookdao;

    @Mock
    private MediaPostService mediaPostService;

    @Before
    public void setUp() throws Exception {
        when(bookdao.get("5f0d452951b6d6035b06b969")).thenReturn(BookRequestUtil.getBookWithId());
        when(bookdao.bulkGet(Collections.singletonList("5f0d452951b6d6035b06b969"))).thenReturn(Collections.singletonList(BookRequestUtil.getBookWithId()));
    }

    @Test
    public void addBook() throws DbException {
        BookCreationRequest bookCreationRequest = BookRequestUtil.getValidBookCreationRequest();
        bookService.addBook(bookCreationRequest);
        verify(bookdao, times(1)).insert(any(Book.class));
    }

    @Test
    public void updateBook() throws DbException, NotFoundException {
        BookUpdationRequest bookUpdationRequest = BookRequestUtil.getValidBookUpdationRequest();
        bookService.updateBook(bookUpdationRequest);
        verify(bookdao, times(1)).save(any(Book.class));
    }

    @Test
    public void getBook() throws NotFoundException {
        Book book = bookService.getBook("5f0d452951b6d6035b06b969");
        Assert.assertEquals("Book1", book.getTitle());
    }

    @Test
    public void bulkGet() throws DbException {
        List<Book> books = bookService.bulkGet(Collections.singletonList("5f0d452951b6d6035b06b969"));
        Assert.assertEquals(books.size(), 1);
    }

    @Test
    public void search() throws DbException {
        bookService.search(BookRequestUtil.getValidBookSearchRequest());
        verify(bookdao, times(1)).search(any(SearchRequest.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchMedia() throws Exception {
        List<String> titles = bookService.searchMedia("0987654321");
    }

    @Test()
    public void searchMediawithMediaPostsEmpty() throws Exception {
        when(bookdao.search(SearchRequestBuilder.build("0987654321"))).thenReturn(Collections.singletonList(BookRequestUtil.getBookWithId()));
        when(mediaPostService.getMediaPosts()).thenReturn(new ArrayList<>());
        List<String> titles = bookService.searchMedia("0987654321");
        Assert.assertEquals(0, titles.size());
    }

    @Test()
    public void searchMediawithMock() throws Exception {
        when(bookdao.search(SearchRequestBuilder.build("0987654321"))).thenReturn(Collections.singletonList(BookRequestUtil.getBookWithId()));
        when(mediaPostService.getMediaPosts()).thenReturn(BookRequestUtil.getMediaPosts());
        List<String> titles = bookService.searchMedia("0987654321");
        Assert.assertEquals(3, titles.size());
    }

    @Test
    public void testBlockInventory() {
        Book book = BookRequestUtil.getBookWithId();
        book.blockInventory("orderId", 15);
        Assert.assertEquals(book.getStock().getStockAvailable(), 5);
        Assert.assertEquals(book.getStock().getStockBlocked(), 15);
        List<InventoryTransaction> inventoryTransaction = book.getInventoryTransactionList().stream()
                .filter(txn -> txn.getOrderId().equals("orderId") && txn.getInventoryStatus().equals(InventoryStatus.BLOCKED)).collect(Collectors.toList());
        Assert.assertEquals(inventoryTransaction.size(), 1);
    }

    @Test
    public void testBookInventory() {
        Book book = BookRequestUtil.getBookWithId();
        book.getStock().setStockAvailable(5);
        book.getStock().setStockBlocked(15);
        book.bookInventory("orderId", 15);
        Assert.assertEquals(book.getStock().getStockAvailable(), 5);
        Assert.assertEquals(book.getStock().getStockBooked(), 15);
        Assert.assertEquals(book.getStock().getStockBlocked(), 0);
        List<InventoryTransaction> inventoryTransaction = book.getInventoryTransactionList().stream()
                .filter(txn -> txn.getOrderId().equals("orderId") && txn.getInventoryStatus().equals(InventoryStatus.BOOKED)).collect(Collectors.toList());
        Assert.assertEquals(inventoryTransaction.size(), 1);
    }

    @Test
    public void testUnblockInventory() {
        Book book = BookRequestUtil.getBookWithId();
        book.getStock().setStockAvailable(5);
        book.getStock().setStockBlocked(15);
        book.getInventoryTransactionList().add(new InventoryTransaction("orderId", InventoryStatus.BLOCKED, 15));
        book.unblockInventory("orderId", 15);
        Assert.assertEquals(book.getStock().getStockAvailable(), 20);
        Assert.assertEquals(book.getStock().getStockBlocked(), 0);
        List<InventoryTransaction> inventoryTransaction = book.getInventoryTransactionList().stream()
                .filter(txn -> txn.getOrderId().equals("orderId") && txn.getInventoryStatus().equals(InventoryStatus.UN_BLOCKED)).collect(Collectors.toList());
        Assert.assertEquals(inventoryTransaction.size(), 1);
    }

    @Test
    public void testUnblockInventoryWhenNotBlocked() {
        Book book = BookRequestUtil.getBookWithId();
        book.unblockInventory("orderId", 15);
        Assert.assertEquals(book.getStock().getStockAvailable(), 20);
        Assert.assertEquals(book.getStock().getStockBlocked(), 0);
        List<InventoryTransaction> inventoryTransaction = book.getInventoryTransactionList().stream()
                .filter(txn -> txn.getOrderId().equals("orderId") && txn.getInventoryStatus().equals(InventoryStatus.UN_BLOCKED)).collect(Collectors.toList());
        Assert.assertEquals(inventoryTransaction.size(), 0);
    }

    /**
     * (?i) for case insensitive
     */

    @Test
    public void regex() {
        String para = "nisi error delectus possimus ut eligendi vitae placeat eos harum cupiditate facilis reprehenderit voluptatem beatae nmodi ducimus quo illum voluptas eligendi net nobis quia fugit";
        List<String> stringList = new ArrayList<>();
        stringList.add("Delectus possimus");
        stringList.add("FJAKFJDLF");
        String regex = String.format("(?i)(%s)", String.join("|", stringList));
        Pattern pt = Pattern.compile(regex);
        Matcher match = pt.matcher(para);
        System.out.println(match.find());
    }
}