package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.model.ProductDTO;
public class ProductMapper {
    public static CustomerProduct mapToProduct(ProductDTO dto, Customer entity) {
        return CustomerProduct.builder().productId(dto.id()).productName(dto.name()).customer(entity).build();
    }
}
