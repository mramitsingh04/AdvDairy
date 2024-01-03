package com.generic.khatabook.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.Map;

public interface CustomerClient {
    @GetExchange("/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);

    @PatchExchange(value = "/{khatabookId}/customer/{customerId}", contentType = "application/json-patch+json")
    public ResponseEntity<?> updatePartialCustomer(@PathVariable String khatabookId, @PathVariable String customerId,
                                                   @RequestBody Map<String, Object> customerEntities);
}
