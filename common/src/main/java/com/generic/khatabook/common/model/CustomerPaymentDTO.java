package com.generic.khatabook.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentDTO {

    private Long id;
    private String khatabookId;
    private String customerId;
    private String paymentType;
    private AmountDTO amount;
    private String productId;
    private LocalDateTime paymentOnDate;
    private String descriptions;


}
