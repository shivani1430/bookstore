package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.pojo.User;
import com.bookstore.bookstore.pojo.apiRequest.OrderCreationDocument;
import com.bookstore.bookstore.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author shivani_reddy
 */

@SpringBootTest
class OrderServiceTest {

    @Autowired
    IOrderService orderService;

    @Test
    void createOrder() {
        User user = User.builder()
                .userId("userid")
                .email("shivani@gmail.com")
                .phoneNo("0987654321")
                .name("shivani")
                .build();
        OrderCreationDocument orderCreationDocument = OrderCreationDocument.builder()
                .bookId("5f0d9bb6ce579865d2155756")
                .transactionId("1234567890")
                .user(user)
                .build();
        try {
            Order order = orderService.createOrder(orderCreationDocument);
            System.out.println(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}