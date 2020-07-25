package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.repository.Idao;
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
    private Idao<Order> orderdao;

    @Override
    public Order createOrder(OrderCreationRequest orderCreationRequest) throws DbException {
        Order order = Order.builder()
                .bookId(orderCreationRequest.getBookId())
                .transactionId(orderCreationRequest.getTransactionId())
                .user(orderCreationRequest.getUser())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return orderdao.insert(order);
    }
}
