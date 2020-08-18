package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.OrderItem;
import com.bookstore.bookstore.repository.StoreDao;
import com.bookstore.bookstore.repository.Bookdao;
import com.bookstore.bookstore.service.IInventoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shivani_reddy
 */
@Service
public class InventoryService implements IInventoryService {

    private Logger log = LogManager.getLogger(InventoryService.class);

    @Autowired
    private Bookdao bookdao;

    @Autowired
    private StoreDao storeDao;


    @Override
    public boolean blockInventory(List<OrderItem> orderItems, String orderId) throws DbException, InventoryNotAvailable, NotFoundException {
        List<Book> books = bookdao.bulkGet(orderItems.stream().map(OrderItem::getBookId).collect(Collectors.toList()));
        Map<String, Book> request = new HashMap<>();
        for (Book book : books) {
            request.put(book.getId(), book);
        }
        for (OrderItem orderItem : orderItems) {
            try {
                Book book = request.get(orderItem.getBookId());
                InventoryUtilsService.checkAvailability(orderItem.getQuantity(), book);
                book.blockInventory(orderId, orderItem.getQuantity());
                bookdao.save(book);
                log.info("InventoryService:blockInventory - inventory blocked for book : {}, quantity : {}", book, orderItem.getQuantity());
            } catch (Exception e) {
                log.error("InventoryService:blockInventory - inventory blocking failed for orderItem : {}, error : {}", orderItem, e.getMessage());
                revertInventory(orderId);
                throw new DbException("inventory blocking failed");
            }
        }
        return true;
    }

    @Override
    public boolean bookInventory(String orderId) throws DbException, NotFoundException {
        Order order = storeDao.get(orderId, Order.class);
        List<Book> books = bookdao.bulkGet(order.getOrderItems().stream().map(OrderItem::getBookId).collect(Collectors.toList()));

        Map<String, Book> request = new HashMap<>();
        for (Book book : books) {
            request.put(book.getId(), book);
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            try {
                Book book = request.get(orderItem.getBookId());
                book.bookInventory(orderId, orderItem.getQuantity());
                bookdao.save(book);
                log.info("InventoryService:bookInventory - inventory booked for book : {}, quantity : {}", book, orderItem.getQuantity());
            } catch (Exception e) {
                log.error("InventoryService:bookInventory - booking failed for orderid : {}, error : {}", orderId, e.getMessage());
                throw new DbException("inventory booking failed");
            }
        }
        return true;
    }

    @Override
    @Async
    public void revertInventory(String orderId) throws DbException, NotFoundException {
        Order order = storeDao.get(orderId, Order.class);
        List<Book> books = bookdao.bulkGet(order.getOrderItems().stream().map(OrderItem::getBookId).collect(Collectors.toList()));

        Map<String, Book> request = new HashMap<>();
        for (Book book : books) {
            request.put(book.getId(), book);
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            try {
                Book book = request.get(orderItem.getBookId());
                book.unblockInventory(orderId, orderItem.getQuantity());
                bookdao.save(book);
                log.info("InventoryService:revertInventory - inventory unblocked for book : {}, quantity : {}", book, orderItem.getQuantity());
            } catch (Exception e) {
                log.error("InventoryService:revertInventory - unblock failed for orderid : {}, error : {}", orderId, e.getMessage());
            }
        }
    }

}
