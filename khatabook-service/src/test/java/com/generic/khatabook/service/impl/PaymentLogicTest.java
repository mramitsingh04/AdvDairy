/*
package com.generic.khatabook.service.impl;

import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.model.*;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentLogicTest {
    public static final String PRODUCT_ID = "prodId";
    @Mock
    ProductClient productClient;
    ProductDTO customerProduct;
    CustomerDTO customerDTO;
    PaymentDTO paymentDTO;
    CustomerSpecificationDTO customerSpecificationDTO;
    @InjectMocks
    private PaymentLogic paymentLogic;

    @Before
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        paymentLogic = new PaymentLogic(productClient);
    }

    @Test
    @DisplayName("Should Return Existing Payment when Customer Payment Product exist")
    void testWhenCustomerDoesHavePaymentWithoutProductId() {
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, null, AmountDTO.ZERO);
        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, null, null);
        assertNotNull(finalPayment);
        assertEquals(paymentDTO, finalPayment);
    }

    @Test
    @DisplayName("Should Return Existing Payment when Customer Specification not exist")
    void testWhenCustomerDoesNotHaveCustomerSpecification() {
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345", "fName", "lName", null, "abc");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, "prodId", AmountDTO.ZERO);
        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, null, null);
        assertNotNull(finalPayment);
        assertEquals(paymentDTO, finalPayment);
    }

    @Test
    @DisplayName("Should Return Existing Payment when Customer Specification not exist")
    void testWhenCustomerDoesHaveCustomerSpecification() {
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345", "fName", "lName", null, "abc");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, "prodId", AmountDTO.TEN);
        List<CustomerProductSpecificationDTO> products = new ArrayList<>();

        products.add(new CustomerProductSpecificationDTO(123l, "123", 2, new UnitOfValue(BigDecimal.TWO, 0, 10), UnitOfMeasurement.KILOGRAM));
        customerSpecificationDTO = new CustomerSpecificationDTO("123", "customer", "Desc", 0, customerDTO.customerId(), customerDTO.khatabookId(), "", products, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, null, customerSpecificationDTO);
        assertNotNull(finalPayment);
        assertEquals(paymentDTO, finalPayment);
    }

    @Test
    @DisplayName("Should Return New Payment when Customer Specification not exist")
    void testWhenCustomerDoesHaveCustomerSpecification1() {
        LocalDateTime now = LocalDateTime.now();
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345", "fName", "lName", null, "abc");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, PRODUCT_ID, AmountDTO.TEN);
        customerProduct = new ProductDTO("prodId", "ProdName", 1, BigDecimal.TEN, UnitOfMeasurement.KILOGRAM, 0);


        List<CustomerProductSpecificationDTO> products = new ArrayList<>();
        CustomerProductSpecificationDTO productSpecificationDTO = new CustomerProductSpecificationDTO(123l, PRODUCT_ID, 2, new UnitOfValue(BigDecimal.TWO, 0, 0), UnitOfMeasurement.KILOGRAM);
        products.add(productSpecificationDTO);

        customerSpecificationDTO = new CustomerSpecificationDTO("customerProdId", "customerProdSpec", "Desc", 0, customerDTO.customerId(), customerDTO.khatabookId(), "", products, now, now, now);


        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, customerProduct, customerSpecificationDTO);
        assertNotNull(finalPayment);
        assertNotEquals(paymentDTO, finalPayment);
        assertEquals(paymentDTO.productId(), finalPayment.productId());
        assertEquals(customerProduct.productId(), finalPayment.productId());
        BigDecimal expected = productSpecificationDTO.unitOfValue().price().multiply(BigDecimal.valueOf(productSpecificationDTO.quantity()));
        assertEquals(expected, finalPayment.amount().value(), "Expected amount not match.");
    }


    @Test
    @DisplayName("Should Return New Payment when Customer Specification not exist")
    void testReturnNewPaymentWhenCustomerHaveDifferentCustomerProductSpecification() {
        LocalDateTime now = LocalDateTime.now();
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345", "fName", "lName", null, "abc");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, PRODUCT_ID, AmountDTO.TEN);
        customerProduct = new ProductDTO("prodId", "ProdName", 1, BigDecimal.TEN, UnitOfMeasurement.KILOGRAM, 0);


        List<CustomerProductSpecificationDTO> products = new ArrayList<>();
        CustomerProductSpecificationDTO productSpecificationDTO = new CustomerProductSpecificationDTO(123l, "OTHER_PRODUCT_ID", 2, new UnitOfValue(BigDecimal.TWO, 0, 0), UnitOfMeasurement.KILOGRAM);
        products.add(productSpecificationDTO);

        customerSpecificationDTO = new CustomerSpecificationDTO("customerProdId", "customerProdSpec", "Desc", 0, customerDTO.customerId(), customerDTO.khatabookId(), "", products, now, now, now);


        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, customerProduct, customerSpecificationDTO);
        assertNotNull(finalPayment);
        assertEquals(paymentDTO, finalPayment);
        assertEquals(paymentDTO.productId(), finalPayment.productId());
        assertEquals(customerProduct.productId(), finalPayment.productId());
        BigDecimal expected = paymentDTO.amount().value();
        assertEquals(expected, finalPayment.amount().value(), "Expected amount not match.");
    }


    @Test
    @DisplayName("Should Return New Payment when Customer Specification does not have amount specification.")
    void testWhenCustomerDoesHaveCustomerSpecification2() {
        LocalDateTime now = LocalDateTime.now();
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345", "fName", "lName", null, "abc");
        paymentDTO = new PaymentDTO(customerDTO.customerId(), null, PRODUCT_ID, AmountDTO.TEN);
        customerProduct = new ProductDTO("prodId", "ProdName", 1, BigDecimal.TEN, UnitOfMeasurement.KILOGRAM, 0);


        List<CustomerProductSpecificationDTO> products = new ArrayList<>();
        CustomerProductSpecificationDTO productSpecificationDTO = new CustomerProductSpecificationDTO(123l, PRODUCT_ID, 2, null, UnitOfMeasurement.KILOGRAM);
        products.add(productSpecificationDTO);

        customerSpecificationDTO = new CustomerSpecificationDTO("customerProdId", "customerProdSpec", "Desc", 0, customerDTO.customerId(), customerDTO.khatabookId(), "", products, now, now, now);


        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO, customerProduct, customerSpecificationDTO);
        assertNotNull(finalPayment);
        assertNotEquals(paymentDTO, finalPayment);
        assertEquals(paymentDTO.productId(), finalPayment.productId());
        assertEquals(customerProduct.productId(), finalPayment.productId());
        BigDecimal expected = customerProduct.price().multiply(BigDecimal.valueOf(productSpecificationDTO.quantity()));
        assertEquals(expected, finalPayment.amount().value(), "Expected amount not match.");
    }


    @Test
    @DisplayName("Should Return Null Payment when Customer Payment is Null")
    void testWhenCustomerDoesNotHavePayment() {
        customerDTO = new CustomerDTO("CustId", "KhataBookId", "2345");
        assertThrows(InvalidArgumentValueException.class, () -> paymentLogic.calculateFinalPayment(customerDTO, null, null, null));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentValueException when customer not found.")
    void testWhenCustomerNotPresentThrowInvalidArgumentValueException() {
        assertThrows(InvalidArgumentValueException.class, () -> paymentLogic.calculateFinalPayment(null, paymentDTO, customerProduct, customerSpecificationDTO), "Should Throw exception");
    }
}*/
