package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.factory.CustomerFactory;
import com.generic.khatabook.factory.CustomerSpecificationFactory;
import com.generic.khatabook.factory.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class CustomerRepositoryTest {
    public static final String KHATABOOK_ID = "Khatabook01";
    public static final String MSISDN = "9911805289";
    public static final String MILK = "milk";
    public static final String SUGAR = "sugar";
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {


    }


    @Test
    void testCustomerWithoutProduct() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN);
        Customer savedCustomer = customerRepository.save(customer);

        Customer getCustomerById = customerRepository.findById(savedCustomer.getCustomerId()).orElse(null);
        Customer getCustomerByMSISDN = customerRepository.findByMsisdn(MSISDN);

        assertNotNull(savedCustomer);
        assertEquals(KHATABOOK_ID, savedCustomer.getKhatabookId());
        assertEquals(MSISDN, savedCustomer.getMsisdn());
        assertNull(savedCustomer.getProducts());
        assertNull(savedCustomer.getCustomerSpecification());

        assertNotNull(getCustomerById);
        assertEquals(KHATABOOK_ID, getCustomerById.getKhatabookId());
        assertEquals(MSISDN, getCustomerById.getMsisdn());
        assertNull(getCustomerById.getProducts());
        assertNull(getCustomerById.getCustomerSpecification());

        assertNotNull(getCustomerByMSISDN);
        assertEquals(KHATABOOK_ID, getCustomerByMSISDN.getKhatabookId());
        assertEquals(MSISDN, getCustomerByMSISDN.getMsisdn());
        assertNull(getCustomerByMSISDN.getProducts());
        assertNull(getCustomerByMSISDN.getCustomerSpecification());

    }

    @Test
    void testCustomerWithProduct() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN, ProductFactory.getCustomerProductsEntity(MILK, SUGAR));
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        assertEquals(KHATABOOK_ID, savedCustomer.getKhatabookId());
        assertEquals(MSISDN, savedCustomer.getMsisdn());
        List<CustomerProduct> products = savedCustomer.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        CustomerProduct milkProduct = products.get(0);
        assertNotNull(milkProduct);
        assertNotNull(milkProduct.getCusProdId());
        assertEquals(MILK, milkProduct.getProductId());
        assertEquals(MILK, milkProduct.getProductName());


        CustomerProduct sugarProduct = products.get(1);
        assertNotNull(sugarProduct);
        assertNotNull(sugarProduct.getCusProdId());
        assertEquals(SUGAR, sugarProduct.getProductId());
        assertEquals(SUGAR, sugarProduct.getProductName());

        assertNull(savedCustomer.getCustomerSpecification());


    }

    @Test
    void testCustomerWithProductAndSpecification() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN, ProductFactory.getCustomerProductsEntity(MILK, SUGAR), CustomerSpecificationFactory.getCustomerSpecification());
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        assertEquals(KHATABOOK_ID, savedCustomer.getKhatabookId());
        assertEquals(MSISDN, savedCustomer.getMsisdn());
        List<CustomerProduct> products = savedCustomer.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        CustomerProduct milkProduct = products.get(0);
        assertNotNull(milkProduct);
        assertNotNull(milkProduct.getCusProdId());
        assertEquals(MILK, milkProduct.getProductId());
        assertEquals(MILK, milkProduct.getProductName());


        CustomerProduct sugarProduct = products.get(1);
        assertNotNull(sugarProduct);
        assertNotNull(sugarProduct.getCusProdId());
        assertEquals(SUGAR, sugarProduct.getProductId());
        assertEquals(SUGAR, sugarProduct.getProductName());

        CustomerSpecification customerSpecification = savedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        assertNotNull(customerSpecification.getCustomerSpecificationId());
        assertEquals("Test", customerSpecification.getSpecificationName());
        assertEquals("LocalProduct", customerSpecification.getSpecificationFor());

        assertNull(customerSpecification.getCustomerProductSpecifications());


    }

    @Test
    void testCreateCustomerWithProductAndSpecificationAndCustomerProductSpecification() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN, ProductFactory.getCustomerProductsEntity(MILK, SUGAR), CustomerSpecificationFactory.getCustomerSpecification(MILK, SUGAR));
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        List<CustomerProduct> products = savedCustomer.getProducts();
        assertNotNull(products);
        CustomerProduct milkProduct = products.get(0);
        assertNotNull(milkProduct);
        CustomerProduct sugarProduct = products.get(1);
        assertNotNull(sugarProduct);

        CustomerSpecification customerSpecification = savedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        assertNotNull(customerSpecification.getCustomerSpecificationId());

        List<CustomerProductSpecification> customerProductSpecifications = customerSpecification.getCustomerProductSpecifications();
        assertNotNull(customerProductSpecifications);
        CustomerProductSpecification milkProductSpec = customerProductSpecifications.get(0);
        assertNotNull(milkProductSpec);
        assertNotNull(milkProductSpec.getCustomerProductSpecId());
        assertEquals(MILK, milkProductSpec.getProductId());
        assertEquals(BigDecimal.valueOf(50), milkProductSpec.getPrice());


        CustomerProductSpecification sugarProductSpec = customerProductSpecifications.get(1);
        assertNotNull(sugarProductSpec);
        assertNotNull(sugarProductSpec.getCustomerProductSpecId());
        assertEquals(SUGAR, sugarProductSpec.getProductId());
        assertEquals(BigDecimal.valueOf(40), sugarProductSpec.getPrice());


    }

    @Test
    void testUpdateCustomerWithProductAndSpecificationAndCustomerProductSpecification() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN, ProductFactory.getCustomerProductsEntity(MILK, SUGAR), CustomerSpecificationFactory.getCustomerSpecification(MILK, SUGAR));
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        List<CustomerProduct> products = savedCustomer.getProducts();
        assertNotNull(products);
        CustomerProduct milkProduct = products.get(0);
        assertNotNull(milkProduct);
        CustomerProduct sugarProduct = products.get(1);
        assertNotNull(sugarProduct);

        CustomerSpecification customerSpecification = savedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        assertNotNull(customerSpecification.getCustomerSpecificationId());

        List<CustomerProductSpecification> customerProductSpecifications = customerSpecification.getCustomerProductSpecifications();
        assertNotNull(customerProductSpecifications);
        CustomerProductSpecification milkProductSpec = customerProductSpecifications.get(0);
        milkProductSpec.setQuantity(3.0f);

        CustomerProductSpecification sugarProductSpec = customerProductSpecifications.get(1);
        sugarProductSpec.setQuantity(2.0f);
        sugarProductSpec.setPrice(BigDecimal.valueOf(44));


        Customer updatedCustomer = customerRepository.save(savedCustomer);

        assertNotNull(updatedCustomer);
        customerSpecification = updatedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        customerProductSpecifications = customerSpecification.getCustomerProductSpecifications();
        assertNotNull(customerProductSpecifications);
        milkProductSpec = customerProductSpecifications.get(0);

        assertEquals(3.0f, milkProductSpec.getQuantity());
        assertEquals(BigDecimal.valueOf(50), milkProductSpec.getPrice());


        sugarProductSpec = customerProductSpecifications.get(1);
        assertEquals(2.0f, sugarProductSpec.getQuantity());
        assertEquals(BigDecimal.valueOf(44), sugarProductSpec.getPrice());


    }

    @Test
    void testUpdateCustomerWithProductAndSpecificationAndAddNewCustomerProductSpecification() {

        Customer customer = CustomerFactory.getCustomer(KHATABOOK_ID, MSISDN, ProductFactory.getCustomerProductsEntity(MILK, SUGAR, "ShowRoom"), CustomerSpecificationFactory.getCustomerSpecification(MILK, SUGAR));
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        List<CustomerProduct> products = savedCustomer.getProducts();
        assertNotNull(products);
        CustomerProduct milkProduct = products.get(0);
        assertNotNull(milkProduct);
        CustomerProduct sugarProduct = products.get(1);
        assertNotNull(sugarProduct);

        CustomerSpecification customerSpecification = savedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        assertNotNull(customerSpecification.getCustomerSpecificationId());

        List<CustomerProductSpecification> customerProductSpecifications = customerSpecification.getCustomerProductSpecifications();
        assertNotNull(customerProductSpecifications);
        CustomerProductSpecification milkProductSpec = customerProductSpecifications.get(0);
        milkProductSpec.setQuantity(3.0f);

        CustomerProductSpecification sugarProductSpec = customerProductSpecifications.get(1);
        sugarProductSpec.setQuantity(2.0f);
        sugarProductSpec.setPrice(BigDecimal.valueOf(44));

        customerProductSpecifications.add(CustomerSpecificationFactory.getProducts(ProductFactory.SHOW_ROOM));


        Customer updatedCustomer = customerRepository.save(savedCustomer);

        assertNotNull(updatedCustomer);
        customerSpecification = updatedCustomer.getCustomerSpecification();
        assertNotNull(customerSpecification);
        customerProductSpecifications = customerSpecification.getCustomerProductSpecifications();
        assertNotNull(customerProductSpecifications);
        milkProductSpec = customerProductSpecifications.get(0);

        assertEquals(3.0f, milkProductSpec.getQuantity());
        assertEquals(BigDecimal.valueOf(50), milkProductSpec.getPrice());


        sugarProductSpec = customerProductSpecifications.get(1);
        assertEquals(2.0f, sugarProductSpec.getQuantity());
        assertEquals(BigDecimal.valueOf(44), sugarProductSpec.getPrice());

        CustomerProductSpecification showRoomProductSpec = customerProductSpecifications.get(2);
        assertEquals(1.0f, showRoomProductSpec.getQuantity());
        assertEquals(BigDecimal.valueOf(50000), showRoomProductSpec.getPrice());


    }


}