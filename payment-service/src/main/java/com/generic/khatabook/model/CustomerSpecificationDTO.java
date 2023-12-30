package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerSpecificationDTO(String id, String name,
                                       String description, int version,
                                       String specificationFor,
                                       @JsonInclude(JsonInclude.Include.NON_EMPTY) List<CustomerProductSpecificationDTO> products,
                                       LocalDateTime createdOn,
                                       LocalDateTime updateOn,
                                       LocalDateTime deleteOn) {


    public CustomerSpecificationUpdatable updatable() {
        final List<CustomerProductSpecificationDTO> products = this.products;
        return new CustomerSpecificationUpdatable(this.id, this.name, this.description, this.version, null, null, this.specificationFor, products.stream().map(CustomerProductSpecificationDTO::updatable).collect(Collectors.toList()), createdOn, updateOn, deleteOn);
    }

    public CustomerSpecificationDTO copyOf(final String id) {
        return new CustomerSpecificationDTO(id, this.name, this.description, this.version, this.specificationFor, this.products, createdOn, updateOn, deleteOn);
    }


}
