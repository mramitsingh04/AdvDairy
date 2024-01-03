package com.generic.khatabook.rating.model;

public record RatingDTO(String id, String customerId, RatingEntityType entityType, String entityId, float rating,
                        String description) {

}
