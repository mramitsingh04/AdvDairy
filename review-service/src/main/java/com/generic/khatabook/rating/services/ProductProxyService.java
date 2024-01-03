package com.generic.khatabook.rating.services;


import com.generic.khatabook.rating.model.ProductView;

public interface ProductProxyService {
    ProductView getProductById(String productId);
}
