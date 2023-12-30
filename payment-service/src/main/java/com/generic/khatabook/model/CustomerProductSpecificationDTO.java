package com.generic.khatabook.model;

import java.util.Objects;

public record CustomerProductSpecificationDTO(String id, String productId,
                                              float quantity,
                                              UnitOfValue unitOfValue,
                                              UnitOfMeasurement unitOfMeasurement) {


    public static CustomerProductSpecificationDTO nonProduct() {
        return new CustomerProductSpecificationDTO(null, null, 1, UnitOfValue.non(), UnitOfMeasurement.NONE);
    }

    public CustomerProductSpecificationUpdatable updatable() {
        return new CustomerProductSpecificationUpdatable(this.id, this.productId, this.quantity, this.unitOfValue,
                this.unitOfMeasurement);
    }

    public boolean hasCustomerSpecificPrice() {
        return Objects.nonNull(unitOfValue()) && Objects.nonNull(unitOfValue().price());
    }
}

