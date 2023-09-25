package com.generic.khatabook.model;

import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerPaymentDTO;

public record CustomerPaymentAggregatedDTO(AggregatePaymentDTO aggregatePaymentDTO, CustomerPaymentDTO customerPayment) {

}
