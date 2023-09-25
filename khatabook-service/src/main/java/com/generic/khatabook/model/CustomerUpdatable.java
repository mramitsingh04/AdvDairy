package com.generic.khatabook.model;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
public class CustomerUpdatable {
    private String customerId;
    private String khatabookId;
    private String msisdn;
    private String firstName;
    private String lastName;
    private List<Product> products;
    private CustomerSpecificationUpdatable specification;

    public CustomerDTO build() {
        if (isNull(specification)) {
            return new CustomerDTO(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, null);
        }
        return new CustomerDTO(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, this.specification.build());
    }

    public CustomerUpdatable addProduct(String productId, String name) {
        products.add(new Product(productId, name));
        return this;
    }
}