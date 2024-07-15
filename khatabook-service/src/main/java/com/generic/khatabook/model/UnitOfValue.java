package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

@JsonRootName(value = "unitOfValue")
public record UnitOfValue(BigDecimal price,
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          Long start,
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          Long end) {

    public static UnitOfValue non() {
        return new UnitOfValue(BigDecimal.ZERO, 0l, 0l);
    }

}


