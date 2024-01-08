package com.generic.khatabook.repository;

import com.generic.khatabook.entity.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProduct, String> {

    @Query(value = "select * from customer_products where customer_id = ?1",
            countQuery = "select count(*) from customers where customer_id = ?1",
            nativeQuery = true)
    List<CustomerProduct> findByCustomerId(String customerId);

}
