package com.generic.khatabook.service;

import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
public interface AggregatePaymentService {
    void paymentAggregate(KhatabookDTO khatabook, CustomerDTO customer, AggregatePaymentDTO payment);

    AggregatePaymentDTO getLastAggregation(KhatabookDTO khatabook, CustomerDTO customer);

    void allPaymentAggregate(KhatabookDTO khatabook, CustomerDTO customerDTO, AggregatePaymentDTO payment);
}
