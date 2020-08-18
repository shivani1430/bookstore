package com.bookstore.bookstore.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shivani_reddy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransaction {

    private String orderId;
    private InventoryStatus inventoryStatus;
    private int quantity;
}
