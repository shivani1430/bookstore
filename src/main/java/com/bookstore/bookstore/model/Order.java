package com.bookstore.bookstore.model;

import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.OrderItem;
import com.bookstore.bookstore.model.pojo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author shivani_reddy
 */

@Data
@Document(collection = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String orderId;
    private String userId;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private String paymentType;
    private Amount totalAmount;
    private List<String> transactionIds;
    private String createdBy;
    private String updatedBy;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @Version
    private int version;
}
