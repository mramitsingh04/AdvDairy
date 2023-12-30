package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByMsisdn(String msisdn);

    Customer findByKhatabookIdAndMsisdn(String khatabookId, String msisdn);

    List<Customer> findByKhatabookId(String khatabookId);
}
