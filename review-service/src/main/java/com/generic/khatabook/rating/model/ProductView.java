package com.generic.khatabook.rating.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductView(String id, String name, Float rating) {

    public static ProductView of(String id) {
        return new ProductView(id, null, null);
    }
  public static ProductView of(String id, String name) {
        return new ProductView(id, name, null);
    }

    public static ProductView of(String id, String name, float rating) {
        return new ProductView(id, name, rating);
    }
}
