package com.generic.khatabook.model;

import java.time.LocalDateTime;

public record CustomerPaymentSummary(String customerId,
                                     String khatabookId,
                                     PaymentType paymentType,
                                     AmountDTO amount,
                                     String productId,
                                     LocalDateTime paymentOnDate) {
}
