package com.generic.khatabook.product.repository;

import com.generic.khatabook.product.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, String> {
}
