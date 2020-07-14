package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;

/**
 * @author shivani_reddy
 */
public interface IOrderdao {

    Order insert(Order book) throws DbException;
}
