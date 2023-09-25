package com.generic.khatabook.service;

import com.generic.khatabook.model.Product;
import com.generic.khatabook.model.ProductDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getCustomerProduct(Product productId);

    default List<ProductDTO> getCustomerProducts(final List<Product> products) {
        if (isNull(products) || products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream().map(this::getCustomerProduct).collect(Collectors.toList());

    }


}
