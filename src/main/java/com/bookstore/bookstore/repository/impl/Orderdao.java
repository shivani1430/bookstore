package com.bookstore.bookstore.repository.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.repository.IOrderdao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

/**
 * @author shivani_reddy
 */

@Repository
public class Orderdao implements IOrderdao {

    private Logger log = LogManager.getLogger(Orderdao.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Order insert(Order order) throws DbException {
        try {
            order = mongoOperations.insert(order);
            return order;
        } catch (Exception e) {
            log.error("Orderdao:insert - insert operation failed for :{}, error : {}", order, e.getMessage());
            throw new DbException("inert operation failed");
        }
    }
}
