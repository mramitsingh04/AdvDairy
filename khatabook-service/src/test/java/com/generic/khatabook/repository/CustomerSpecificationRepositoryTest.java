package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CustomerSpecificationRepositoryTest {

    @Autowired
    CustomerSpecificationRepository customerSpecificationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldSuccessfulInsertSuccessfulCustomerSpecification() {


        CustomerProductSpecification milkProd = CustomerProductSpecification.builder()
                .productId("milkId")
                .price(BigDecimal.valueOf(40))
                .quantity(5.0f)
                .unitOfMeasurement("L")
                .build();
        CustomerProductSpecification sugerProd = CustomerProductSpecification.builder()
                .productId("sugerId")
                .price(BigDecimal.valueOf(40))
                .quantity(3.0f)
                .unitOfMeasurement("L")
                .build();
        CustomerProductSpecification roomProd = CustomerProductSpecification.builder()
                .productId("roomId")
                .price(BigDecimal.valueOf(5000))
                .quantity(2.0f)
                .unitOfMeasurement("I")
                .build();
        CustomerProductSpecification meterProd = CustomerProductSpecification.builder()
                .productId("milkId")
                .price(BigDecimal.valueOf(10))
                .quantity(1.0f)
                .unitOfMeasurement("R")
                .build();


        CustomerSpecification customerSpecification = CustomerSpecification.builder()
                .customerSpecificationId("customerSpecificationId")
                .createdOn(LocalDateTime.now())
                .description("Amit Specification")
                .specificationName("Amit Specification")
                .customerProductSpecifications(List.of(milkProd, sugerProd, roomProd, meterProd))
                .build();

        CustomerSpecification save = customerSpecificationRepository.save(customerSpecification);
        List<CustomerProduct> listOfProduct = new ArrayList<>();
        listOfProduct.add(CustomerProduct.builder().productId(milkProd.getProductId()).build());
        listOfProduct.add(CustomerProduct.builder().productId(roomProd.getProductId()).build());
        listOfProduct.add(CustomerProduct.builder().productId(sugerProd.getProductId()).build());
        listOfProduct.add(CustomerProduct.builder().productId(meterProd.getProductId()).build());
        Customer customer = Customer.builder()
                .customerId("CUS01")
                .firstName("Amit")
                .lastName("Singh")
                .khatabookId("Appna")
                .products(listOfProduct)
                .customerSpecification(save)
                .build();

        Customer savedCustomer = customerRepository.save(customer);


        CustomerSpecification custSpec = customerSpecificationRepository.findById(save.getCustomerSpecificationId()).orElse(null);
        assertNotNull(custSpec);
//        CustomerSpecification customerSpecification1 = custSpec.get(0);
//        assertEquals(customerSpecification1.getCustomer().getCustomerId(), customerSpecification.getCustomer().getCustomerId());
//        assertEquals(customerSpecification1.getCustomer().getKhatabookId(), customerSpecification.getCustomer().getKhatabookId());
//        assertEquals(0, customerSpecification1.getVersion());
//        customerSpecification1.setSpecificationName("New Update");
//        customerSpecificationRepository.save(customerSpecification1);
//        custSpec = customerSpecificationRepository.findByCustomerId("customerId");
//        assertNotNull(custSpec);
//        assertEquals(1, customerSpecification1.getVersion());

    }


}