package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.Mapper;
import com.generic.khatabook.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO, CustomerUpdatable> {

    @Autowired
    private CustomerSpecificationMapper specificationMapper;

    @Override
    public Customer mapToEntity(final CustomerDTO myCustomer) {
        if (myCustomer==null) {
            return null;
        }
        CustomerSpecification specification = null;
        if (nonNull(myCustomer.specification())) {
            specification = specificationMapper.mapToEntity(myCustomer.specification());
        }
        return Customer.builder()
                .customerSpecification(specification)
                .customerId(myCustomer.customerId())
                .khatabookId(myCustomer.khatabookId())
                .firstName(myCustomer.firstName())
                .lastName(myCustomer.lastName())
                .msisdn(myCustomer.msisdn()).build();
    }

    @Override
    public Container<CustomerDTO, CustomerUpdatable> mapToContainer(final Customer customer) {
        if (isNull(customer)) {
            return Container.empty();
        }

        final CustomerDTO customerDTO = mapToDTO(customer);

        return Container.of(customerDTO, customerDTO.updatable());
    }

    @Override
    public CustomerDTO mapToDTO(final Customer customer) {
        if (isNull(customer)) {
            return null;
        }

        CustomerSpecificationDTO specification = specificationMapper.mapToDTO(customer.getCustomerSpecification());
        List<Product> products = customer.getProducts()!=null ? customer.getProducts().stream().map(this::buildProduct).collect(Collectors.toList()) : Collections.emptyList();
        return new CustomerDTO(customer.getCustomerId().toString(), customer.getKhatabookId(), customer.getMsisdn(),
                customer.getFirstName(), customer.getLastName(), products, specification);
    }

    private Product buildProduct(CustomerProduct customerProduct) {
        return new Product(customerProduct.getProductId(), customerProduct.getProductName());
    }
}
