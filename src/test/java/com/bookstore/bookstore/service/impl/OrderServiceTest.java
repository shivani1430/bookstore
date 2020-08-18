package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.InventoryNotAvailable;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.OrderStatus;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationRequest;
import com.bookstore.bookstore.repository.StoreDao;
import com.bookstore.bookstore.service.IInventoryService;
import com.bookstore.bookstore.testUtils.BookRequestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    private StoreDao storeDao;

    @Mock
    private IInventoryService inventoryService;

    @Before
    public void setUp() throws Exception {
        when(storeDao.insert(any())).thenReturn(BookRequestUtil.getOrderWithId());
        when(storeDao.get(any(),Order.class)).thenReturn(BookRequestUtil.getOrderWithId());
    }

    @Test
    public void createOrder() throws DbException, InventoryNotAvailable, NotFoundException {
        OrderCreationRequest orderCreationRequest = BookRequestUtil.getValidOrderCreationRequest();
        orderService.createOrder(orderCreationRequest);
        verify(storeDao, times(1)).insert(any(Order.class));
        verify(storeDao, times(1)).save(any(Order.class));
    }
}