package com.generic.khatabook.notebook.exchanger;

import com.generic.khatabook.notebook.model.KhatabookCustomerView;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/customer-service", accept = "application/json", contentType = "application/json")
public interface CustomerClient {
    @GetExchange(url = "/{khatabookId}/{customerId}")
    ResponseEntity<EntityModel<KhatabookCustomerView>> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);
    @GetExchange("/{customerId}")
    ResponseEntity<?> getCustomerById(@PathVariable String customerId) ;
}
