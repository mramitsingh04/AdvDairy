package com.generic.khatabook.rating.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.Map;

@HttpExchange(url = "/customer-service", accept = "application/json", contentType = "application/json")
public interface CustomerClient {
    @GetExchange(url = "/{khatabookId}/{customerId}")
    ResponseEntity<?> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);
    @GetExchange("/{customerId}")
    ResponseEntity<?> getCustomerById(@PathVariable String customerId) ;
}
