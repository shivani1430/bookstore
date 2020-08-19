package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.OrderItem;
import com.bookstore.bookstore.model.pojo.OrderStatus;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.repository.Bookdao;
import com.bookstore.bookstore.repository.StoreDao;
import com.bookstore.bookstore.service.IInventoryService;
import com.bookstore.bookstore.service.IOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shivani_reddy
 */

@Service
public class OrderService implements IOrderService {

    private Logger log = LogManager.getLogger(OrderService.class);

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private Bookdao bookdao;

    @Autowired
    private IInventoryService inventoryService;

    @Override
    public Order createOrder(OrderCreationRequest orderCreationRequest) throws DbException, InventoryNotAvailable, NotFoundException {
        List<Book> books = bookdao.bulkGet(orderCreationRequest.getOrderItems().stream().map(OrderItem::getBookId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(books)) {
            throw new IllegalArgumentException("invalid orderItems");
        }
        if (books.size() != orderCreationRequest.getOrderItems().size()) {
            throw new IllegalArgumentException("some of the books in orderItems may be inactive");
        }
        Amount totalAmount = new Amount();
        totalAmount.setAmount(books.stream().mapToDouble(e -> e.getPrice().getAmount()).sum());
        totalAmount.setCurrency(books.get(0).getPrice().getCurrency());

        if (!doPriceMatch(orderCreationRequest.getTotalAmount().getAmount(), totalAmount.getAmount())) {
            throw new IllegalArgumentException("total Price for orderItems is " + totalAmount.getAmount());
        }

        Order order = Order.builder()
                .userId(orderCreationRequest.getUserId())
                .orderItems(orderCreationRequest.getOrderItems())
                .orderStatus(OrderStatus.Created)
                .totalAmount(totalAmount)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        order = storeDao.insert(order);
        log.info("OrderService:createOrder - order created : {}", order);
        try {
            inventoryService.blockInventory(order.getOrderItems(), order.getOrderId());
        } catch (Exception e) {
            order.setOrderStatus(OrderStatus.InventoryBlockFailed);
            log.info("OrderService:createOrder - order status to InventoryBlockFailed : {}", order);
            storeDao.save(order);
            throw e;
        }
        order.setOrderStatus(OrderStatus.InventoryBlocked);
        log.info("OrderService:createOrder - order status to InventoryBlocked : {}", order);
        order = storeDao.save(order);

        return order;
    }

    @Override
    public Order storeCashTransaction(String orderId, Amount amount) throws DbException, NotFoundException {
        Order order = storeDao.get(orderId, Order.class);

        order.setPaymentType("cash");
        if (doPriceMatch(amount.getAmount(), order.getTotalAmount().getAmount())) {
            inventoryService.bookInventory(orderId);
            log.info("OrderService:storeCashTransaction - booked inventory for order : {}", order);
            order.setOrderStatus(OrderStatus.Confirmed);
            order = storeDao.save(order);
            log.info("OrderService:storeCashTransaction - order Confirmed : {}", order);
        }

        return order;
    }

    private boolean doPriceMatch(double paidAmount, double totalAmount) {
        double diff = totalAmount - paidAmount;
        return diff <= 0;
    }
}
