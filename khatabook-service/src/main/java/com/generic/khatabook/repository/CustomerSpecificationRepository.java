package com.generic.khatabook.repository;

import com.generic.khatabook.entity.CustomerSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSpecificationRepository extends JpaRepository<CustomerSpecification, String> {
}