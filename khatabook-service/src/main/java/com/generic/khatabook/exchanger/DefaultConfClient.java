package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/default-service", accept = "application/json", contentType = "application/json")
public interface DefaultConfClient {
    @GetExchange("/all")
    ResponseEntity<ProductDTO> getAllProducts();

    @GetExchange("/{productId}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable String productId);


}
