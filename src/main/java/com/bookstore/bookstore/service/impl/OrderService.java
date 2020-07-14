package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.OrderCreationDocument;
import com.bookstore.bookstore.repository.IOrderdao;
import com.bookstore.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shivani_reddy
 */

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderdao orderdao;

    @Override
    public Order createOrder(OrderCreationDocument orderCreationDocument) throws DbException {
        Order order = Order.builder()
                .bookId(orderCreationDocument.getBookId())
                .transactionId(orderCreationDocument.getTransactionId())
                .user(orderCreationDocument.getUser())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return orderdao.insert(order);
    }
}
