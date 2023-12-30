package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Khatabook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface KhatabookRepository extends JpaRepository<Khatabook, UUID> {
    Optional<Khatabook> findByMsisdn(String msisdn);

    Optional<Khatabook> findByKhatabookId(String khatabookId);
}
