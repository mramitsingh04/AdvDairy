package com.generic.khatabook.service.impl;

import com.generic.khatabook.model.Product;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductClient productClient;

    @Autowired
    public ProductServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Cacheable
    public ProductDTO getCustomerProduct(final Product product) {
        if (isNull(product)) {
            return null;
        }

        try {
            final ResponseEntity<ProductDTO> responseEntity = productClient.getProductById(product.productId());
            if (isNull(responseEntity)) {
                throw new NotFoundException(AppEntity.PRODUCT, product.productId());
            } else {
                return responseEntity.getBody();
            }
        } catch (WebClientResponseException e) {
            throw new NotFoundException(AppEntity.PRODUCT, product.productId());
        }

    }


}
