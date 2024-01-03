package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.KhatabookDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface KhatabookClient {
    @GetExchange("/{khatabookId}/customers")
    ResponseEntity<KhatabookDetails> getKhatabookDetails(@PathVariable String khatabookId);

}
