package com.generic.khatabook.product.services;

import com.generic.khatabook.product.model.Container;
import com.generic.khatabook.product.model.ProductDTO;
import com.generic.khatabook.product.model.ProductUpdatable;

import java.util.List;
public interface ProductManagementService {

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductByName(String productName);

    ProductDTO saveProduct(ProductDTO product);

    Container<ProductDTO, ProductUpdatable> findProductById(String productId);

    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    List<ProductDTO> findProductByUnitOfMeasurement(String unitOfMeasurement);
}
