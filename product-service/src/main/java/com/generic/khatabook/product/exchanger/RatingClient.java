package com.generic.khatabook.product.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/rating-service", accept = "application/json", contentType = "application/json")
public interface RatingClient {
    @GetExchange("/product/{entityId}")
    ResponseEntity<?> findAllRatingByProductId(@PathVariable String entityId);

    @GetExchange("/customer/{customerId}")
    ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId);
    @GetExchange("/product/{entityId}/lite")
    ResponseEntity<Float> getRatingForProductId(@PathVariable String entityId) ;
}
