package com.generic.khatabook.service.impl;

import com.generic.khatabook.exceptions.NoPaymentForAggregateException;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerPaymentAggregatedDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.entity.AggregatePayment;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.repository.AggregatePaymentRepository;
import com.generic.khatabook.repository.PaymentRepository;
import com.generic.khatabook.service.AggregatePaymentService;
import com.generic.khatabook.service.mapper.AggregatePaymentMapper;
import com.generic.khatabook.service.mapper.CustomerPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregatePaymentServiceImpl implements AggregatePaymentService {

    private final AggregatePaymentRepository aggregatePaymentRepository;
    private final AggregatePaymentMapper aggregatePaymentMapper;
    private final CustomerPaymentMapper customerPaymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentLogic paymentLogic;

    @Override
    public void paymentAggregate(final KhatabookDTO khatabook, final CustomerDTO customer, final AggregatePaymentDTO dto) {

        LocalDateTime startDate = dto.from() == null ? LocalDateTime.MIN : dto.from();
        LocalDateTime endDate = dto.to() == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MAX) : dto.to();

        final List<CustomerPayment> customerAllPaymentBetweenRange = getCustomerPayments(customer, dto, startDate, endDate);
        aggregatePaymentForCustomerAndProducts(customer, dto, customerAllPaymentBetweenRange);
    }

    private List<CustomerPayment> getCustomerPayments(CustomerDTO customer, AggregatePaymentDTO dto, LocalDateTime startDate, LocalDateTime endDate) {

        return paymentRepository.findByKhatabookIdAndCustomerIdAndProductIdAndPaymentOnDateBetween(customer.khatabookId(), customer.customerId(), dto.productId(), startDate, endDate);
    }

    @Override
    public void allPaymentAggregate(KhatabookDTO khatabook, CustomerDTO customer, AggregatePaymentDTO dto) {
        LocalDateTime startDate = dto.from() == null ? LocalDateTime.MIN : dto.from();
        LocalDateTime endDate = dto.to() == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MAX) : dto.to();

        final List<CustomerPayment> customerAllPaymentBetweenRange = getCustomerAllPayments(customer, dto, startDate, endDate);
        aggregatePaymentForCustomerAndProducts(customer, dto, customerAllPaymentBetweenRange);
    }

    private void aggregatePaymentForCustomerAndProducts(CustomerDTO customer, AggregatePaymentDTO dto, List<CustomerPayment> customerAllPaymentBetweenRange) {
        if (!customerAllPaymentBetweenRange.isEmpty()) {
            CustomerPaymentAggregatedDTO customerPaymentAggregatedDTO = paymentLogic.aggregatePayment(customer, dto, customerAllPaymentBetweenRange);
            paymentRepository.save(customerPaymentMapper.mapToEntity(customerPaymentAggregatedDTO.customerPayment()));
            aggregatePaymentRepository.save(aggregatePaymentMapper.mapToEntity(customerPaymentAggregatedDTO.aggregatePaymentDTO(),
                    customer));
            paymentRepository.deleteAll(customerAllPaymentBetweenRange);
        } else {
            throw new NoPaymentForAggregateException("Product %s don't have any payment for aggregated.".formatted(dto.productId()));
        }
    }

    private List<CustomerPayment> getCustomerAllPayments(CustomerDTO customer, AggregatePaymentDTO dto, LocalDateTime startDate, LocalDateTime endDate) {
        final List<CustomerPayment> customerAllPaymentBetweenRange;
        if (dto.productId() != null) {
            customerAllPaymentBetweenRange = paymentRepository.findByKhatabookIdAndCustomerIdAndProductIdAndPaymentOnDateBetween(customer.khatabookId(), customer.customerId(), dto.productId(), startDate, endDate);
        } else {
            customerAllPaymentBetweenRange = paymentRepository.findByKhatabookIdAndCustomerIdAndPaymentOnDateBetween(
                    customer.khatabookId(),
                    customer.customerId(),
                    startDate,
                    endDate);
        }
        return customerAllPaymentBetweenRange;
    }

    @Override
    public AggregatePaymentDTO getLastAggregation(final KhatabookDTO khatabook, final CustomerDTO customer) {
        final AggregatePayment entity =
                aggregatePaymentRepository.findOne(Example.of(AggregatePayment.builder().khatabookId(khatabook.khatabookId())
                        .customerId(customer.customerId())
                        .build())).orElse(null);
        assert entity != null;
        return aggregatePaymentMapper.mapToDTO(entity);
    }
}
