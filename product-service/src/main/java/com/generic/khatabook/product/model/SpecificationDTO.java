package com.generic.khatabook.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpecificationDTO(String id, String name, String description, LocalDateTime createdOn,
                               LocalDateTime updatedOn) {
}
