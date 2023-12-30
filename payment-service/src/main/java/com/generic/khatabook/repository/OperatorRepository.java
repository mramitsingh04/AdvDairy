package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, String> {
    Operator findByMsisdn(String msisdn);
}
