package com.generic.khatabook.rating.model;

public record RatingDTO(String id, String fromCustomerId, RatingEntityType entityType, String entityId, float rating,
                        String description) {

}
