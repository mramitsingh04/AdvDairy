package com.generic.khatabook.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record UnitOfValue(BigDecimal price,
                          @JsonInclude(JsonInclude.Include.NON_NULL) Long start,
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          Long end) {

    public static UnitOfValue non() {
        return new UnitOfValue(BigDecimal.ZERO, 0l, 0l);
    }

}


