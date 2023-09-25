package com.generic.khatabook.model;

import com.generic.khatabook.model.CustomerPaymentSummary;
import com.generic.khatabook.model.PaymentStatistics;

import java.util.Collection;
import java.util.List;

public record KhatabookPaymentSummary(PaymentStatistics paymentStatistics,
                                      Collection<CustomerPaymentSummary> customers) {
    static KhatabookPaymentSummary empty() {
        return new KhatabookPaymentSummary(PaymentStatistics.of(), List.of());
    }
}
