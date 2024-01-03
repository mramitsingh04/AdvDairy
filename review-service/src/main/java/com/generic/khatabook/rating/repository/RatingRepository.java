package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByEntityId(String productId);

    List<Rating> findByCustomerId(String customerId);

    Rating findByCustomerIdAndEntityId(String customerId, String productId);

}
