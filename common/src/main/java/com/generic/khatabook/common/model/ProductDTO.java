package com.generic.khatabook.common.model;

import java.math.BigDecimal;

public record ProductDTO(String id, String name, int quantity, BigDecimal price, UnitOfMeasurement unitOfMeasurement, float rating) {

    public ProductDTO copyOf(final String productId) {
        return new ProductDTO(productId, name, quantity, price, unitOfMeasurement, rating);
    }


    public ProductUpdatable updatable() {
        return new ProductUpdatable(this.id, this.name, this.quantity, this.price, this.unitOfMeasurement, this.rating);
    }

}
