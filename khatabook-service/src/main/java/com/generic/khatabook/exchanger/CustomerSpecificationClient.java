package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.CustomerSpecificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CustomerSpecificationClient {
    @GetExchange("/{khatabookId}/{customerId}/specification/{specificationId}")
    public ResponseEntity<CustomerSpecificationDTO> getById(@PathVariable String khatabookId,
                                                            @PathVariable String customerId,
                                                            @PathVariable String specificationId);
}
