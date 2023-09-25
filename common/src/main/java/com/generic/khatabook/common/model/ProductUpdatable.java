package com.generic.khatabook.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductUpdatable {
    private String id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private UnitOfMeasurement unitOfMeasurement;
    private float rating;

    public ProductDTO build() {
        return new ProductDTO(this.id, this.name, quantity, this.price, this.unitOfMeasurement, rating);
    }
}
