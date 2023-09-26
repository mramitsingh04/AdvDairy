package com.generic.khatabook.product.repository;

import com.generic.khatabook.product.entity.Product;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productManagementRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void saveProductWithValidInput() {
        Product milkProduct = productManagementRepository.save(Product.builder()
                .name("Milk")
                .price(BigDecimal.valueOf(30))
                .quantity(1)
                .unitOfMeasurement("L")
                .build());

        assertNotNull(milkProduct);
        assertNotNull(milkProduct.getId());
        assertEquals(BigDecimal.valueOf(30), milkProduct.getPrice());
        assertEquals("Milk", milkProduct.getName());
        assertEquals(1, milkProduct.getQuantity());
        assertEquals("L", milkProduct.getUnitOfMeasurement());
    }

    @Test
    void shouldThrowExceptionFonNegativeQuantity() {
        Product milk = Product.builder()
                .name("Milk")
                .quantity(-1)
                .price(BigDecimal.valueOf(30))
                .unitOfMeasurement("L")
                .build();
        productManagementRepository.save(milk);


        assertThrows(ConstraintViolationException.class, () -> productManagementRepository.findByName(milk.getName()));


    }

    @Test
    void shouldThrowExceptionForNullUnitOfMeasurement() {
        Product milk = Product.builder()
                .name("Milk")
                .quantity(1)
                .price(BigDecimal.valueOf(30))
                .build();

        productManagementRepository.save(milk);
        assertThrows(ConstraintViolationException.class, () -> productManagementRepository.findByName(milk.getName()));


    }

    @Test
    void shouldThrowExceptionForDuplicateProductName() {
        Product milk = Product.builder()
                .name("Milk")
                .quantity(1)
                .price(BigDecimal.valueOf(30))
                .unitOfMeasurement("L")
                .build();
        Product milkProduct = Product.builder()
                .name("Milk")
                .quantity(1)
                .price(BigDecimal.valueOf(40))
                .unitOfMeasurement("L")
                .build();
        productManagementRepository.save(milk);
        productManagementRepository.save(milkProduct);

        assertThrows(InvalidDataAccessResourceUsageException.class, () -> productManagementRepository.findByName(milk.getName()));

    }

}