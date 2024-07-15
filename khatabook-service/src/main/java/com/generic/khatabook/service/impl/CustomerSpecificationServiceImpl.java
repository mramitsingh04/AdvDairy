package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.DuplicateFoundException;
import com.generic.khatabook.model.*;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.repository.CustomerSpecificationRepository;
import com.generic.khatabook.service.CustomerSpecificationService;
import com.generic.khatabook.service.ProductService;
import com.generic.khatabook.service.mapper.CustomerSpecificationMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class CustomerSpecificationServiceImpl implements CustomerSpecificationService {

    private CustomerSpecificationRepository myCustomerSpecificationRepository;
    private CustomerSpecificationMapper myMapper;

    private CustomerRepository customerRepository;
    private ProductService productService;

/*
    @Autowired
    public CustomerSpecificationServiceImpl(final CustomerSpecificationRepository thatCustomerSpecificationRepository,
                                            final CustomerSpecificationMapper customerSpecificationMapper, CustomerRepository customerRepository) {
        this.myCustomerSpecificationRepository = thatCustomerSpecificationRepository;
        myMapper = customerSpecificationMapper;
        this.customerRepository = customerRepository;
    }
*/

    @Override
    @Transactional
    public CustomerSpecificationDTO save(final CustomerSpecificationDTO dto) {
        //  CustomerSpecification entity = myMapper.mapToEntity(dto);

        //return myMapper.mapToDTO(myCustomerSpecificationRepository.save(entity));
        return null;
    }

    @Override
    public CustomerSpecificationDTO getCustomer(CustomerDTO customer) {

        CustomerSpecificationDTO specification = customer.specification();
        List<CustomerSpecificationDTO> lst = getByCustomerId(customer.customerId()).keys();
        if (specification != null && !lst.isEmpty()) {

            List<ProductDTO> customerProducts = productService.getCustomerProducts(customer.products());
            return new CustomerSpecificationDTO(specification.id(), specification.name(), specification.description(), specification.version(), specification.specificationFor(), customerProducts.stream().map(this::toDto).toList(), null, null, null);

        }
        return null;
    }

    private CustomerProductSpecificationDTO toDto(ProductDTO dto) {
        return new CustomerProductSpecificationDTO(null, dto.id(), dto.quantity(), new UnitOfValue(dto.price(), null, null), dto.unitOfMeasurement());
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
    @Transactional(value = Transactional.TxType.REQUIRED)
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
