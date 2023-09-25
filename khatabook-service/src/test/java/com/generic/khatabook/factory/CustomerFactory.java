package com.generic.khatabook.factory;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerSpecification;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerFactory {
    public static Customer getCustomer(String khatabookId, String msisdn) {
        return Customer.builder().msisdn(msisdn).khatabookId(khatabookId).firstName("Amit").lastName("Singh").createdOn(LocalDateTime.now()).build();
    }

    public static Customer getCustomer(String khatabookId, String msisdn, List<CustomerProduct> productList) {
        return Customer.builder().msisdn(msisdn).khatabookId(khatabookId).firstName("Amit").lastName("Singh").products(productList).createdOn(LocalDateTime.now()).build();
    }

    public static Customer getCustomer(String khatabookId, String msisdn, List<CustomerProduct> productList, CustomerSpecification specification) {
        return Customer.builder().msisdn(msisdn).khatabookId(khatabookId).firstName("Amit").lastName("Singh").products(productList)
                .customerSpecification(specification)
                .createdOn(LocalDateTime.now()).build();
    }

    public static final CustomerSpecification getCustomerSpecification() {
        return CustomerSpecification.builder().build();
    }


}
