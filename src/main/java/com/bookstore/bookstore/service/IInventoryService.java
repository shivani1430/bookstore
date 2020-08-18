package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.pojo.OrderItem;

import java.util.List;

/**
 * @author shivani_reddy
 */
public interface IInventoryService {

    boolean blockInventory(List<OrderItem> orderItems, String orderId) throws DbException, InventoryNotAvailable, NotFoundException;

    void revertInventory(String orderId) throws DbException, NotFoundException;

    boolean bookInventory(String orderId) throws DbException, NotFoundException;
}
