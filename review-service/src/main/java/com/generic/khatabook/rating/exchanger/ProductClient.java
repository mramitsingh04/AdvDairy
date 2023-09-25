package com.generic.khatabook.rating.exchanger;

import com.generic.khatabook.rating.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/product-service/products")
    ResponseEntity<ProductDTO> getAllProducts();

    @GetExchange("/product-service/product/{productId}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable String productId);


}
