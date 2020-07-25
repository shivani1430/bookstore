package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.repository.Idao;
import com.bookstore.bookstore.service.IOrderService;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @InjectMocks
    IOrderService orderService;

    @Mock
    private Idao<Order> orderdao;

    @Test
    public void createOrder() throws DbException {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderService.createOrder(orderCreationRequest);
        verify(orderdao, times(1)).insert(any(Order.class));
    }
}