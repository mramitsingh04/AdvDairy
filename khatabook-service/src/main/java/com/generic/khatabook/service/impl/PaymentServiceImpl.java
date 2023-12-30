package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.model.*;
import com.generic.khatabook.repository.PaymentRepository;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.service.ProductService;
import com.generic.khatabook.service.mapper.CustomerPaymentMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository myPaymentRepository;
    @Autowired
    private CustomerPaymentMapper customerPaymentMapper;
    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentNewLogic paymentLogic;

    @Override
    public KhatabookPaymentSummary getPaymentDetailByKhatabookId(final String khatabookId) {

        val customersPayment = myPaymentRepository.findByKhatabookId(khatabookId);
        final PaymentStatistics paymentStatistics = paymentLogic.getPaymentStatistics(customersPayment);
        return new KhatabookPaymentSummary(paymentStatistics, CustomerPaymentMapper.mapToPojos(customersPayment, null, null));
    }


    @Override
    public boolean savePayment(final KhatabookDTO khatabookDTO, final CustomerDTO customerDTO, final PaymentDTO paymentDTO, final PaymentType paymentType) {

        log.info("Save Payment for khatabook {}", khatabookDTO.khatabookId());

        PaymentsDTO finalPayments = paymentLogic.calculateEachCustomerProduct(customerDTO, paymentDTO, productService.getCustomerProducts(customerDTO.products()));
        if (!finalPayments.hasAmounts()) {
            throw new InvalidArgumentValueException(AppEntity.AMOUNT, "pass the +ve value.");
        }


        final List<CustomerPayment> customerPayments = new ArrayList<>();
        for (PaymentDTO finalPayment : finalPayments) {
            final Amount amount = Amount.of(finalPayment.products().get(0).amount().value(), finalPayment.products().get(0).amount().unitOfMeasurement());
            customerPayments.add(CustomerPayment.builder().khatabookId(khatabookDTO.khatabookId()).customerId(customerDTO.customerId()).amount(amount).paymentType(paymentType.name()).productId(finalPayment.products().get(0).productId()).paymentOnDate(LocalDateTime.now(Clock.systemDefaultZone())).build());
        }


        myPaymentRepository.saveAll(customerPayments);


        return true;
    }

    @Override
    public KhatabookPaymentSummary getPaymentDetailForCustomer(final CustomerDTO customerRequest) {


        return getPaymentDetailForCustomer(customerRequest, "asc", "date", null);

    }

    @Override
    public KhatabookPaymentSummary getPaymentDetailForCustomer(final CustomerDTO customerRequest, final String sorting, final String sortingBy, final CustomerSpecificationDTO customerSpecification) {
        final Collection<CustomerPayment> allCustomerPayment = myPaymentRepository.findByKhatabookIdAndCustomerId(customerRequest.khatabookId(), customerRequest.customerId());
        if (isNull(allCustomerPayment) || allCustomerPayment.isEmpty()) {
            return null;
        }

        return new KhatabookPaymentSummary(paymentLogic.getPaymentStatistics(allCustomerPayment), CustomerPaymentMapper.mapToPojos(allCustomerPayment, SummaryProperties.non(), customerSpecification));
    }

    @Override
    public KhatabookPaymentSummaryView getCustomerPaymentDetailView(final CustomerDTO customerRequest, final CustomerSpecificationDTO customerSpecification) {
        final Collection<CustomerPayment> allCustomerPayment = myPaymentRepository.findByKhatabookIdAndCustomerId(customerRequest.khatabookId(), customerRequest.customerId());
        if (isNull(allCustomerPayment) || allCustomerPayment.isEmpty()) {
            return null;
        }

        return new KhatabookPaymentSummaryView(paymentLogic.getPaymentStatistics(allCustomerPayment), CustomerPaymentMapper.mapToPojos(customerRequest, allCustomerPayment, customerSpecification));
    }

}
