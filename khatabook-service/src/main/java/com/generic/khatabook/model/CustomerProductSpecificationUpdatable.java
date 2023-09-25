package com.generic.khatabook.model;

import com.generic.khatabook.model.CustomerProductSpecificationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerProductSpecificationUpdatable {
    private String id;
    private String productId;
    private float quantity;
    private UnitOfValue unitOfValue;
    private UnitOfMeasurement unitOfMeasurement;

    public CustomerProductSpecificationUpdatable(CustomerProductSpecificationDTO dto) {
        this(dto.id(), dto.productId(), dto.quantity(), dto.unitOfValue(), dto.unitOfMeasurement());
    }

    public CustomerProductSpecificationDTO build() {
        return new CustomerProductSpecificationDTO(this.id, this.productId, this.quantity, unitOfValue, unitOfMeasurement);
    }

}
