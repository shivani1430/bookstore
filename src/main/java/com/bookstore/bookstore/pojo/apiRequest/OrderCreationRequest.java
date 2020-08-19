package com.bookstore.bookstore.pojo.apiRequest;

import com.bookstore.bookstore.model.pojo.Amount;
import com.bookstore.bookstore.model.pojo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shivani_reddy
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {

    private String userId;
    private List<OrderItem> orderItems;
    private Amount totalAmount;
}
