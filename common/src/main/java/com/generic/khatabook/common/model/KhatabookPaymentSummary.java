package com.generic.khatabook.common.model;

import java.util.Collection;
import java.util.List;

public record KhatabookPaymentSummary(PaymentStatistics paymentStatistics,
                                      Collection<CustomerPaymentSummary> customers) {
    static KhatabookPaymentSummary empty() {
        return new KhatabookPaymentSummary(PaymentStatistics.of(), List.of());
    }
}
