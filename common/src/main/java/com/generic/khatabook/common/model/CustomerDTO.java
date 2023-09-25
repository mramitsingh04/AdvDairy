package com.generic.khatabook.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(String customerId, String khatabookId, String msisdn,
                          String firstName, String lastName,
                          @JsonInclude(JsonInclude.Include.NON_EMPTY) List<Product> products,
                          CustomerSpecificationDTO specification) {
    public static final String ANONYMOUS = "Anonymous";


    public CustomerDTO(final String customerId, final String khatabookId, final String msisdn) {
        this(customerId, khatabookId, msisdn, null, null, null, null);
    }


    public static CustomerDTO of(String customerId, String khatabookId, String msisdn, String firstName, String lastName, String productId, CustomerSpecificationDTO specification) {
        return new CustomerDTO(customerId, khatabookId, msisdn, firstName, lastName, Collections.singletonList(new Product(productId)), specification);
    }

    public static CustomerDTO of(final String customerId, final String khatabookId, final String msisdn) {
        return new CustomerDTO(customerId, khatabookId, msisdn);
    }

    public CustomerDTO copyOf(final String generateId) {
        return new CustomerDTO(generateId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, this.specification);
    }

    public CustomerUpdatable updatable() {
        if (isNull(this.specification)) {
            return new CustomerUpdatable(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, null);
        }

        return new CustomerUpdatable(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, new CustomerSpecificationUpdatable(this.specification));
    }


    @Override
    public String toString() {

        String fullName;
        String msisdnDetail = "with %s".formatted(msisdn);
        String custDetail = null;


        if (isNull(firstName) || isNull(lastName)) {
            fullName = "%s %s".formatted(ANONYMOUS, "user");
        } else {
            fullName = "%s %s".formatted(firstName, lastName);
        }
        if (Objects.nonNull(customerId)) {
            custDetail = "and customer productId %s".formatted(customerId);
        }

        final String khatabookDetail = "belong to %s".formatted(khatabookId);
        if (isNull(custDetail)) {
            return "%s %s %s.".formatted(fullName, msisdnDetail, khatabookDetail);
        }
        return "%s %s %s %s.".formatted(fullName, msisdnDetail, custDetail, khatabookDetail);

    }
}



