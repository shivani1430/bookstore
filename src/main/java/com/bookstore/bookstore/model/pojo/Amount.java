package com.bookstore.bookstore.model.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author shivani_reddy
 */

@Data
@Builder
public class Amount {

    private double amount;
    private Currency currency;
}
