package com.generic.khatabook.factory;

import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class CustomerSpecificationFactory {
    public static CustomerSpecification getCustomerSpecification(String... productIds) {

        List<CustomerProductSpecification> listOfProductSpecification = getProducts(productIds);
        return CustomerSpecification.builder().specificationName("Test").specificationFor("LocalProduct").customerProductSpecifications(listOfProductSpecification).build();
    }

    public static List<CustomerProductSpecification> getProducts(String... ids) {


        if (isNull(ids)) {
            return Collections.emptyList();
        }
        return Arrays.stream(ids).map(ProductFactory.productDTO::get).map(CustomerSpecificationFactory::buildCustomerProductSpecification).collect(Collectors.toList());
    }

    private static CustomerProductSpecification buildCustomerProductSpecification(ProductDTO product) {
        return CustomerProductSpecification.builder().productId(product.id()).quantity((float) product.quantity()).price(product.price()).build();
    }

    public static CustomerSpecification getCustomerSpecification() {
        return CustomerSpecification.builder().specificationName("Test").specificationFor("LocalProduct").build();
    }

    public static CustomerProductSpecification getProducts(String ids) {
        return buildCustomerProductSpecification(ProductFactory.productDTO.get(ids));
    }


}
