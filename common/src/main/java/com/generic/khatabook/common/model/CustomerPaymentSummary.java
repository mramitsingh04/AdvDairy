package com.generic.khatabook.common.model;

import java.time.LocalDateTime;

public record CustomerPaymentSummary(String customerId,
                                     String khatabookId,
                                     PaymentType paymentType,
                                     AmountDTO amount,
                                     String productId,
                                     LocalDateTime paymentOnDate) {
}
