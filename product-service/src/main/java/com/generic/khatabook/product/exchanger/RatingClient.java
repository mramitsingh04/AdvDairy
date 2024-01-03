package com.generic.khatabook.product.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface RatingClient {
    @GetExchange("/rating-service/product/{entityId}")
    ResponseEntity<?> findAllRatingByProductId(@PathVariable String entityId);

    @GetExchange("/rating-service/customer/{customerId}")
    ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId);
    @GetExchange("/rating-service/product/{entityId}/lite")
    ResponseEntity<Float> getRatingForProductId(@PathVariable String entityId) ;
}
