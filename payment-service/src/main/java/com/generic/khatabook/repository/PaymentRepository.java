package com.generic.khatabook.repository;

import com.generic.khatabook.entity.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<CustomerPayment, Long> {
    List<CustomerPayment> findByKhatabookId(String khatabookId);

    List<CustomerPayment> findByKhatabookIdAndCustomerId(String khatabookId, String customerId);
    List<CustomerPayment> findByKhatabookIdAndCustomerIdAndPaymentOnDateBetween(String khatabookId, String customerId
            , LocalDateTime startDate, LocalDateTime endDate);
    List<CustomerPayment> findByKhatabookIdAndCustomerIdAndProductIdAndPaymentOnDateBetween(String khatabookId, String customerId,String productId
            , LocalDateTime startDate, LocalDateTime endDate);
}
