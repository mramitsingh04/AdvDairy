package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PaymentStatistics(AmountDTO total, AmountDTO credited,
                                AmountDTO debited) {
    public static PaymentStatistics of() {
        return new PaymentStatistics(AmountDTO.ZERO, AmountDTO.ZERO, AmountDTO.ZERO);
    }
}
