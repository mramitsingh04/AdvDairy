package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.factory.CustomerSpecificationFactory;
import com.generic.khatabook.factory.ProductFactory;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.Product;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.service.ProductService;
import com.generic.khatabook.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.fromString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {


    @InjectMocks
    CustomerServiceImpl service;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;


    @Mock
    ProductService myProductService;


    Customer customer;

    @BeforeEach
    void setUp() {

        List<CustomerProduct> listOfProduct = new ArrayList<>();
        listOfProduct.add(CustomerProduct.builder().cusProdId(fromString("1")).productId("milk").productName("milk").build());
        listOfProduct.add(CustomerProduct.builder().cusProdId(fromString("2")).productId("room").productName("room").build());
        CustomerSpecification customerSpecification = CustomerSpecificationFactory.getCustomerSpecification();


        customer = Customer.builder().customerId("CUS01").khatabookId("Khatabook01").firstName("Amit").lastName(
                "Singh").createdOn(LocalDateTime.now()).products(listOfProduct).customerSpecification(customerSpecification).build();


    }

    // JUnit test for saveEmployee method
    @DisplayName("Update the Customer with products and specification")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        given(customerRepository.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        given(customerRepository.save(customer)).willReturn(customer);
        given(myProductService.getCustomerProduct(ProductFactory.getMilkProductView())).willReturn(ProductFactory.getMilkProduct());
        given(myProductService.getCustomerProduct(ProductFactory.getSugarProductView())).willReturn(ProductFactory.getSugarProduct());


        List<Product> products = new ArrayList<>();
        products.add(ProductFactory.getMilkProductView());
        products.add(ProductFactory.getSugarProductView());
        CustomerSpecificationDTO specification = null;
        CustomerDTO updateCustomerDTO = new CustomerDTO("CUS01", "Khatabook", "1234", "Amit", "Singh", products, specification);

        List<CustomerProduct> listOfEntityProduct = ProductFactory.getCustomerProductsEntity();

        Customer customerEntity =
                Customer.builder().customerId("CUS01").khatabookId("Khatabook01").firstName("Amit").lastName("Singh").createdOn(LocalDateTime.now()).products(listOfEntityProduct).customerSpecification(CustomerSpecification.builder().customerSpecificationId("CS01").specificationName("Test").specificationFor("LocalProduct").build()).build();
        given(customerMapper.mapToEntity(updateCustomerDTO)).willReturn(customerEntity);


        CustomerSpecificationDTO specificationDefault = new CustomerSpecificationDTO("CS01", "Test", "", 0, "LocalProduct", null, null, null, null);
        CustomerDTO savedCustomerDTO = new CustomerDTO("CUS01", "Khatabook", "1234", "Amit", "Sing", ProductFactory.getProductsView("milk", "sugar", "room"), specificationDefault);

        given(customerMapper.mapToDTO(any(Customer.class))).willReturn(savedCustomerDTO);


        CustomerDTO savedCustomer = service.update(updateCustomerDTO);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.products());
        assertNotNull(savedCustomer.specification());
        assertEquals(updateCustomerDTO.customerId(), savedCustomer.customerId());
        assertEquals(updateCustomerDTO.khatabookId(), savedCustomer.khatabookId());
        assertNotEquals(updateCustomerDTO.products(), savedCustomer.products());
        assertNotEquals(updateCustomerDTO.specification(), savedCustomer.specification());

        assertNotNull(savedCustomer.specification().id());
        assertNotNull(savedCustomer.specification().specificationFor());
        assertNotNull(savedCustomer.specification().description());
        assertNotNull(savedCustomer.specification().name());
        assertEquals(0, savedCustomer.specification().version());


    }

}