package com.generic.khatabook.common.model;

public record ProductRatingDTO(String id, String customerId, String productId, float rating, String description) {
    public ProductRatingDTO copyOf(final String id) {
        return new ProductRatingDTO(id, customerId, productId, rating, description);
    }
}
