package com.generic.khatabook.room.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Product {
    private int productId;
    private String productName;

}
