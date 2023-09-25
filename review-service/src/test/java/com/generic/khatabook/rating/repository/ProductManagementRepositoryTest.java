package com.generic.khatabook.rating.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RatingRepositoryTest1{
//    @Autowired
//    RatingRepository repository;
//
//    @Autowired
//    TestEntityManager entityManager;
//
//    @Test
//    void saveProductWithValidInput() {
//        Rating milkProduct = repository.save(Rating.builder()
//                .productId("Milk")
//                .customerId("Customer-01")
//                .rating(3.1f)
//                .build());
//
//        assertNotNull(milkProduct);
//        assertNotNull(milkProduct.getId());
//        assertEquals(BigDecimal.valueOf(30), milkProduct.getPrice());
//        assertEquals("Milk", milkProduct.getName());
//        assertEquals(1, milkProduct.getQuantity());
//        assertEquals("L", milkProduct.getUnitOfMeasurement());
//    }
//
//    @Test
//    void shouldThrowExceptionFonNegativeQuantity() {
//        Product milk = Product.builder()
//                .name("Milk")
//                .quantity(-1)
//                .price(BigDecimal.valueOf(30))
//                .unitOfMeasurement("L")
//                .build();
//        repository.save(milk);
//
//
//        assertThrows(ConstraintViolationException.class, () -> repository.findByName(milk.getName()));
//
//
//    }
//
//    @Test
//    void shouldThrowExceptionForNullUnitOfMeasurement() {
//        Product milk = Product.builder()
//                .name("Milk")
//                .quantity(1)
//                .price(BigDecimal.valueOf(30))
//                .build();
//
//        repository.save(milk);
//        assertThrows(ConstraintViolationException.class, () -> repository.findByName(milk.getName()));
//
//
//    }
//
//    @Test
//    void shouldThrowExceptionForDuplicateProductName() {
//        Product milk = Product.builder()
//                .name("Milk")
//                .quantity(1)
//                .price(BigDecimal.valueOf(30))
//                .unitOfMeasurement("L")
//                .build();
//        Product milkProduct = Product.builder()
//                .name("Milk")
//                .quantity(1)
//                .price(BigDecimal.valueOf(40))
//                .unitOfMeasurement("L")
//                .build();
//        repository.save(milk);
//        repository.save(milkProduct);
//
//        assertThrows(InvalidDataAccessResourceUsageException.class, () -> repository.findByName(milk.getName()));
//
//    }

}