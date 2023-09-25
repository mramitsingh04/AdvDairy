package com.generic.khatabook.model;


import java.time.LocalDateTime;

public record CustomerPaymentSummaryView(String customerId,
                                         PaymentType paymentType,
                                         AmountDTO amount,
                                         Product product,
                                         LocalDateTime paymentOnDate) {
}
