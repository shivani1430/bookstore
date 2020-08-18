package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.OrderStatus;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.repository.StoreDao;
import com.bookstore.bookstore.service.IInventoryService;
import com.bookstore.bookstore.service.IOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shivani_reddy
 */

@Service
public class OrderService implements IOrderService {

    private Logger log = LogManager.getLogger(OrderService.class);

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private IInventoryService inventoryService;

    @Override
    public Order createOrder(OrderCreationRequest orderCreationRequest) throws DbException, InventoryNotAvailable, NotFoundException {
        Order order = Order.builder()
                .userId(orderCreationRequest.getUserId())
                .orderItems(orderCreationRequest.getOrderItems())
                .orderStatus(OrderStatus.Created)
                .totalAmount(orderCreationRequest.getTotalAmount()) // TODO : need to check total amount
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        order = storeDao.insert(order);
        log.info("OrderService:createOrder - order created : {}", order);
        try {
            inventoryService.blockInventory(order.getOrderItems(), order.getOrderId());
        } catch (Exception e) {
            order.setOrderStatus(OrderStatus.InventoryBlockFailed);
            storeDao.save(order);
            throw e;
        }
        order.setOrderStatus(OrderStatus.InventoryBlocked);
        order = storeDao.save(order);

        return order;
    }

    @Override
    public Order storeCashTransaction(String orderId, Amount amount) throws DbException, NotFoundException {
        Order order = storeDao.get(orderId, Order.class);

        order.setPaymentType("cash");
        if (isTotalPaid(amount.getAmount(), order.getTotalAmount().getAmount())) {
            inventoryService.bookInventory(orderId);
            log.info("OrderService:storeCashTransaction - booked inventory for order : {}", order);
            order.setOrderStatus(OrderStatus.Confirmed);
            order = storeDao.save(order);
            log.info("OrderService:storeCashTransaction - order Confirmed : {}", order);
        }

        return order;
    }

    private boolean isTotalPaid(double paidAmount, double totalAmount) {
        double diff = totalAmount - paidAmount;
        return diff <= 0;
    }
}
