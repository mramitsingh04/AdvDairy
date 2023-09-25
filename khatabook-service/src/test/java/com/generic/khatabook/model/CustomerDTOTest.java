package com.generic.khatabook.model;

import com.generic.khatabook.model.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
class CustomerDTOTest {
    @Test
    void testCustomer() {
        CustomerDTO customerDTO = CustomerDTO.of("customerId",
                "khatabookId",
                "9911805***",
                "Amit",
                "Singh",
                null,
                null);

        assertEquals(customerDTO.customerId(), "customerId");
        assertEquals(customerDTO.khatabookId(), "khatabookId");
        assertEquals(customerDTO.msisdn(), "9911805***");
        assertEquals(customerDTO.firstName(), "Amit");
        assertEquals(customerDTO.lastName(), "Singh");
        assertNull(customerDTO.products());
        assertNull(customerDTO.specification());
    }

    @Test
    void testSameCustomer() {
        CustomerDTO customerForAnonymous = CustomerDTO.of(null, "khatabookId", "9911805***");

        CustomerDTO customerDTO = CustomerDTO.of(null, "khatabookId", "9911805***", "Amit", "Singh", null
                , null);


        CustomerDTO customerDTOCopyOf = customerDTO.copyOf("newId");

        assertNull(customerDTO.customerId());
        assertNotNull(customerDTOCopyOf.customerId());
        assertEquals(customerDTO.khatabookId(), customerDTOCopyOf.khatabookId());
        assertEquals(customerDTO.msisdn(), customerDTOCopyOf.msisdn());
        assertEquals(customerDTO.firstName(), customerDTOCopyOf.firstName());
        assertEquals(customerDTO.lastName(), customerDTOCopyOf.lastName());
        assertEquals(customerDTO.products(), customerDTOCopyOf.products());
        assertNull(customerDTO.specification());


        assertEquals("Amit Singh with 9911805*** belong to khatabookId.", customerDTO.toString());
        assertEquals("Amit Singh with 9911805*** and customer productId newId belong to khatabookId.",
                customerDTOCopyOf.toString());
        assertEquals("Anonymous user with 9911805*** belong to khatabookId.",
                customerForAnonymous.toString());


    }
}