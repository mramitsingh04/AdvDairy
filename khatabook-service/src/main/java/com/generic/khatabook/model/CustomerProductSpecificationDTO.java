package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerProductSpecificationDTO(String id, String productId,
                                              @JsonInclude(JsonInclude.Include.NON_DEFAULT) float quantity,
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

