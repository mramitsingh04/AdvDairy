package com.generic.khatabook.repository;

import com.generic.khatabook.entity.AggregatePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatePaymentRepository extends JpaRepository<AggregatePayment, Long> {
}
