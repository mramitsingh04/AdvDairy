package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Khatabook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhatabookRepository extends JpaRepository<Khatabook, Long> {
    Optional<Khatabook> findByMsisdn(String msisdn);

    Optional<Khatabook> findByKhatabookId(String khatabookId);
}
