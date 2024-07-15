package com.generic.khatabook.service;

import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.Containers;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
public interface CustomerSpecificationService {

    Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> get(String id);

    CustomerSpecificationDTO save(CustomerSpecificationDTO customerSpecificationService);

    Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getByCustomerId(final String customerId);
    CustomerSpecificationDTO getCustomer(final CustomerDTO customer);

    Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(String khatabookId, String customerId);

    void delete(CustomerSpecificationDTO customerSpecificationDTO);

    Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(String specificationId);

    CustomerSpecificationDTO update(CustomerSpecificationDTO build);

    void mergeSpecification(CustomerDTO customerDetails, CustomerSpecificationDTO customerSpecificationDTO);
}
