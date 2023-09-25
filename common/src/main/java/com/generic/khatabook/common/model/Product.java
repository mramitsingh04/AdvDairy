package com.generic.khatabook.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Product(String productId, String name) {
    public Product(String productId) {
        this(productId, null);
    }

    public static Product of(String productId) {
        return new Product(productId);
    }
}
