package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationDocument;

/**
 * @author shivani_reddy
 */
public interface IOrderService {

    Order createOrder(OrderCreationDocument orderCreationDocument) throws DbException;
}
