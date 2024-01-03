package com.generic.khatabook.rating.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerView(String customerId, String name, Float rating, String description) {

    public static CustomerView of(String customerId) {
        return new CustomerView(customerId, null, null, null);
    }

    public static CustomerView of(String customerId, String name) {
        return new CustomerView(customerId, name, null, null);
    }

    public static CustomerView of(String customerId, String name, float rating) {
        return new CustomerView(customerId, name, rating, null);
    }
    public static CustomerView of(String customerId, String name, float rating, String description) {
        return new CustomerView(customerId, name, rating, description);
    }

}
