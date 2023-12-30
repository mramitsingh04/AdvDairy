package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record KhatabookPaymentSummaryView(PaymentStatistics paymentStatistics,
                                          Collection<CustomerPaymentSummaryView> customers) {
    static KhatabookPaymentSummaryView empty() {
        return new KhatabookPaymentSummaryView(PaymentStatistics.of(), List.of());
    }
}
