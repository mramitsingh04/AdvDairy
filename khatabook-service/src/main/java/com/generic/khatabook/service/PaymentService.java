package com.generic.khatabook.service;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookPaymentSummary;
import com.generic.khatabook.model.KhatabookPaymentSummaryView;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.PaymentType;

public interface PaymentService {
    public KhatabookPaymentSummary getPaymentDetailByKhatabookId(String khatabookId);

    boolean savePayment(KhatabookDTO khatabookDTO, CustomerDTO customerDTO, PaymentDTO paymentDTO, final PaymentType paymentType);

    KhatabookPaymentSummary getPaymentDetailForCustomer(CustomerDTO customerRequest);

    KhatabookPaymentSummary getPaymentDetailForCustomer(CustomerDTO customerRequest,
                                                        final String sorting,
                                                        final String sortingBy, CustomerSpecificationDTO customerSpecification);

    KhatabookPaymentSummaryView getCustomerPaymentDetailView(CustomerDTO customerRequest,
                                                             CustomerSpecificationDTO customerSpecification);
}
