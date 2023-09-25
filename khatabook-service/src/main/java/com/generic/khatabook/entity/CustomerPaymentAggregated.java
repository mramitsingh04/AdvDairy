package com.generic.khatabook.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_payment_aggregated")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPaymentAggregated {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String khatabookId;
    private String customerId;
    private String paymentType;
    @Embedded
    private Amount amount;
    private String productId;
    private LocalDateTime paymentOnDate;
    private String descriptions;


}

