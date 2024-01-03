package com.generic.khatabook.product.services;

import com.generic.khatabook.product.model.Container;
import com.generic.khatabook.product.model.ProductDTO;
import com.generic.khatabook.product.model.ProductUpdatable;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductByName(String productName);

    ProductDTO saveProduct(ProductDTO product);

    Container<ProductDTO, ProductUpdatable> findProductById(String productId);
    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    List<ProductDTO> findProductByUnitOfMeasurement(String unitOfMeasurement);
}
