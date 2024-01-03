package com.generic.khatabook.rating.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.Map;

@HttpExchange
public interface CustomerClient {
    @GetExchange(url = "/customer-service/{khatabookId}/{customerId}", accept = {"application/json"})
    ResponseEntity<?> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);
    @GetExchange("/customer-service/{customerId}")
    ResponseEntity<?> getCustomerById(@PathVariable String customerId) ;
}
