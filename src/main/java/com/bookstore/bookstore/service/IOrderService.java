package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;

/**
 * @author shivani_reddy
 */
public interface IOrderService {

    Order createOrder(OrderCreationRequest orderCreationRequest) throws DbException, InventoryNotAvailable, NotFoundException;

    Order storeCashTransaction(String orderId, Amount amount) throws DbException, NotFoundException;
}
