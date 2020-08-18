package com.bookstore.bookstore.model;

import com.bookstore.bookstore.model.pojo.*;
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
import java.util.stream.Collectors;

/**
 * @author shivani_reddy
 */

@Data
@Document(collection = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String isbn;
    private String title;
    private String author;
    private String description;
    private Amount price;
    private Stock stock;
    private Status status;
    private List<InventoryTransaction> inventoryTransactionList;
    private String createdBy;
    private String updatedBy;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @Version
    private int version;

    public void blockInventory(String orderId, int quantityToBeBlocked) {
        this.stock.setStockAvailable(this.stock.getStockAvailable() - quantityToBeBlocked);
        this.stock.setStockBlocked(this.stock.getStockBlocked() + quantityToBeBlocked);
        this.inventoryTransactionList.add(new InventoryTransaction(orderId, InventoryStatus.BLOCKED, quantityToBeBlocked));
        this.updatedAt = new Date();
    }

    public void bookInventory(String orderId, int quantityToBeBooked) {
        this.stock.setStockBlocked(this.stock.getStockBlocked() - quantityToBeBooked);
        this.stock.setStockBooked(this.stock.getStockBooked() + quantityToBeBooked);
        this.inventoryTransactionList = this.inventoryTransactionList.stream().filter(txn -> !(txn.getOrderId().equals(orderId))).collect(Collectors.toList());
        this.inventoryTransactionList.add(new InventoryTransaction(orderId, InventoryStatus.BOOKED, quantityToBeBooked));
        this.updatedAt = new Date();
    }

    public void unblockInventory(String orderId, int quantityToBeUnblocked) {
        List<InventoryTransaction> list = this.inventoryTransactionList.stream()
                .filter(txn -> txn.getOrderId().equals(orderId) && txn.getInventoryStatus().equals(InventoryStatus.BLOCKED)).collect(Collectors.toList());
        if (list.size() != 0) {
            this.stock.setStockAvailable(this.stock.getStockAvailable() + quantityToBeUnblocked);
            this.stock.setStockBlocked(this.stock.getStockBlocked() - quantityToBeUnblocked);
            list.get(0).setInventoryStatus(InventoryStatus.UN_BLOCKED);
            this.updatedAt = new Date();
        }

    }
}
