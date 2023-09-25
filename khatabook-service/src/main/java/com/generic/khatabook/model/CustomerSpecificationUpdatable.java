package com.generic.khatabook.model;

import com.generic.khatabook.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.model.CustomerProductSpecificationUpdatable;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.UnitOfMeasurement;
import com.generic.khatabook.model.UnitOfValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
public class CustomerSpecificationUpdatable {
    private String id;
    private String name;
    private String description;
    private int version;
    private String customerId;
    private String khatabookId;
    private String specificationFor;
    private List<CustomerProductSpecificationUpdatable> products;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;

    public CustomerSpecificationUpdatable(CustomerSpecificationDTO dto) {
        this(dto.id(), dto.name(), dto.description(), dto.version(), null, null, dto.specificationFor(), chengeProd(dto.products()), dto.createdOn(), dto.updateOn(), dto.deleteOn());
    }

    private static List<CustomerProductSpecificationUpdatable> chengeProd(List<CustomerProductSpecificationDTO> products) {
        return products.stream().map(CustomerSpecificationUpdatable::changeProductUpdatable).collect(Collectors.toList());
    }

    private static CustomerProductSpecificationUpdatable changeProductUpdatable(CustomerProductSpecificationDTO customerProductSpecificationDTO) {
        return new CustomerProductSpecificationUpdatable(customerProductSpecificationDTO);
    }

    public CustomerSpecificationDTO build() {
        List<CustomerProductSpecificationDTO> newProducts = Collections.emptyList();
        final List<CustomerProductSpecificationUpdatable> products = this.products;
        if (nonNull(products)) {
            newProducts = products.stream().map(CustomerProductSpecificationUpdatable::build).collect(Collectors.toList());
        }

        return new CustomerSpecificationDTO(this.id, this.name, this.description, this.version, this.specificationFor, newProducts, createdOn,
                updatedOn, deletedOn);
    }

    public CustomerProductSpecificationUpdatable getProducts(String productId) {
        if (Objects.isNull(products)) {
            return null;
        }
        return products.stream().filter(x -> x.getProductId().equals(productId)).findFirst().orElse(new CustomerProductSpecificationUpdatable(null, productId, 1, UnitOfValue.non(), UnitOfMeasurement.NONE));
    }

    public void addProduct(final CustomerProductSpecificationUpdatable customerProductSpecificationUpdatable) {
        if (Objects.isNull(products)) {
             products = new ArrayList<>();
        }
        products.add(customerProductSpecificationUpdatable);
    }
}
