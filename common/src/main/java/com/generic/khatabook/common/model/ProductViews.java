package com.generic.khatabook.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductViews {
    private final List<ProductDTO> products;
    private int totalNumberOfProduct;

    public ProductViews(List<ProductDTO> products) {
        this.products = products;
        totalNumberOfProduct = products.size();
    }

}
