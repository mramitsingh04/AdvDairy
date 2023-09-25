package com.generic.khatabook.model;

import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.PaymentType;

import java.time.LocalDateTime;

public record CustomerPaymentSummary(String customerId,
                                     String khatabookId,
                                     PaymentType paymentType,
                                     AmountDTO amount,
                                     String productId,
                                     LocalDateTime paymentOnDate) {
}
