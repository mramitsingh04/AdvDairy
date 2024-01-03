package com.generic.khatabook.product.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.Map;

public interface CustomerClient {
    @GetExchange("/{khatabookId}/{customerId}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);

    @PatchExchange(value = "/{khatabookId}/{customerId}", contentType = "application/json-patch+json")
    public ResponseEntity<?> updatePartialCustomer(@PathVariable String khatabookId, @PathVariable String customerId,
                                                   @RequestBody Map<String, Object> customerEntities) ;
}
