package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.DuplicateFoundException;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.Containers;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.repository.CustomerSpecificationRepository;
import com.generic.khatabook.service.CustomerSpecificationService;
import com.generic.khatabook.service.mapper.CustomerSpecificationMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.isNull;
@Service
public class CustomerSpecificationServiceImpl implements CustomerSpecificationService {

    private CustomerSpecificationRepository myCustomerSpecificationRepository;
    private CustomerSpecificationMapper myMapper;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerSpecificationServiceImpl(final CustomerSpecificationRepository thatCustomerSpecificationRepository,
                                            final CustomerSpecificationMapper customerSpecificationMapper, CustomerRepository customerRepository) {
        this.myCustomerSpecificationRepository = thatCustomerSpecificationRepository;
        myMapper = customerSpecificationMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerSpecificationDTO save(final CustomerSpecificationDTO dto) {
        //  CustomerSpecification entity = myMapper.mapToEntity(dto);

        //return myMapper.mapToDTO(myCustomerSpecificationRepository.save(entity));
        return null;
    }

    @Override
    public Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getByCustomerId(final String customerId) {
        return myMapper.mapToContainers(myCustomerSpecificationRepository.findById(customerId).orElse(null));
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(final String specificationId) {
        return myMapper.mapToContainer(myCustomerSpecificationRepository.findById(specificationId).orElse(null));
    }

    @Override
    public CustomerSpecificationDTO update(final CustomerSpecificationDTO dto) {
        return myMapper.mapToDTO(myCustomerSpecificationRepository.save(myMapper.mapToEntity(dto)));
    }

    @Override
    public void delete(final CustomerSpecificationDTO customerSpecificationDTO) {
        myCustomerSpecificationRepository.delete(myMapper.mapToEntity(customerSpecificationDTO));
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> get(final String id) {
        return myMapper.mapToContainer(myCustomerSpecificationRepository.findById(id).orElse(null));
    }

    @Override
    public Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(final String khatabookId,
                                                                                                         final String customerId) {
//        return myMapper.mapToContainers(myCustomerSpecificationRepository.findByKhatabookIdAndCustomerId(khatabookId,
// 0               customerId));
        return Containers.empty();
    }

    @Override
    public void mergeSpecification(CustomerDTO customerDetails, CustomerSpecificationDTO dto) {

        if (myCustomerSpecificationRepository.existsById(dto.id())) {
            throw new DuplicateFoundException(AppEntity.CUSTOMER_SPECIFICATION, dto.id());
        }

        Customer customer = customerRepository.findById(customerDetails.customerId()).orElse(null);
        if (isNull(customer.getCustomerSpecification())) {
            CustomerSpecification customerSpecification = myMapper.mapToEntity(dto);
            myCustomerSpecificationRepository.save(customerSpecification);
            customerSpecification.setCustomerSpecificationId(customer.getCustomerId());
            customer.setCustomerSpecification(customerSpecification);
        }

        Customer saved = customerRepository.save(customer);


        CustomerSpecification dbspecification = myCustomerSpecificationRepository.findById(customerDetails.customerId()).orElse(CustomerSpecification.builder().build());


    }
}
