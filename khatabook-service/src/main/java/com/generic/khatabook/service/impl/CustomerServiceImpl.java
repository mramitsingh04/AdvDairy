package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.Product;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.model.UnitOfMeasurement;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.ProductService;
import com.generic.khatabook.service.mapper.CustomerMapper;
import com.generic.khatabook.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository myCustomerRepository;
    private final ProductService myProductService;
    private final CustomerMapper myCustomerMapper;

    //Observability is the ability to measure the internal state of a system only by its external outputs
    //https://www.baeldung.com/spring-boot-3-observabilitye
//    private final ObservationRegistry myRegistry;


    public CustomerServiceImpl(CustomerRepository myCustomerRepository, ProductService myProductService, CustomerMapper myCustomerMapper) {
        this.myCustomerRepository = myCustomerRepository;
        this.myProductService = myProductService;
        this.myCustomerMapper = myCustomerMapper;
    }

    @Override
    public boolean isValid(CustomerDTO customerDTO) {
        return myCustomerMapper.mapToDTO(myCustomerRepository.findByMsisdn(customerDTO.msisdn()))!=null;
    }

    @Override
    public Container<CustomerDTO, CustomerUpdatable> getByCustomerId(final String customerId) {
        Customer customer =
                myCustomerRepository.findById(customerId).orElse(Customer.builder().build());
        for (CustomerProduct customerProduct : customer.getProducts()) {
            ProductDTO product = myProductService.getCustomerProduct(Product.of(customerProduct.getProductId()));
            customerProduct.setProductName(product.name());
        }
        return myCustomerMapper.mapToContainer(customer);
    }

//    private CustomerDTO getObservation(final String matrixName, final Supplier<CustomerDTO> supplierTask) {
//        return Observation.createNotStarted(matrixName, myRegistry).observe(supplierTask);
//    }

    @Override
    public CustomerDTO getByMsisdn(String msisdn) {
        Customer customer = myCustomerRepository.findByMsisdn(msisdn);
        for (CustomerProduct customerProduct : customer.getProducts()) {
            ProductDTO product = myProductService.getCustomerProduct(Product.of(customerProduct.getProductId()));
            customerProduct.setProductName(product.name());
        }
        return myCustomerMapper.mapToDTO(customer);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {

        final Customer customerEntity = myCustomerMapper.mapToEntity(customerDTO);
        List<ProductDTO> products = customerDTO.products().stream().map(myProductService::getCustomerProduct).toList();

        if (customerDTO.products()!=null) {
            List<CustomerProduct> list = new ArrayList<>();
            for (ProductDTO x : products) {
                populateProductLastReading(x);
                list.add(ProductMapper.mapToProduct(x, customerEntity));
            }
            customerEntity.setProducts(list);
        }
        setDefaultCustomerSpecification(customerEntity, products);
        return myCustomerMapper.mapToDTO(myCustomerRepository.save(customerEntity));
    }

    private ProductDTO populateProductLastReading(final ProductDTO x) {
        if (x.unitOfMeasurement().equals(UnitOfMeasurement.READING)) {
//            myProductService.find
        }
        return x;
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {

        final Customer existingEntity =
                myCustomerRepository.findById(customerDTO.customerId()).orElse(null);
        final Customer customerEntity = myCustomerMapper.mapToEntity(customerDTO);


        if (nonNull(existingEntity) && !Objects.equals(customerDTO.firstName(), existingEntity.getFirstName())) {
            existingEntity.setFirstName(customerDTO.firstName());
        }
        if (nonNull(existingEntity) && !Objects.equals(customerDTO.lastName(), existingEntity.getLastName())) {
            existingEntity.setLastName(customerDTO.lastName());
        }
        if (nonNull(existingEntity)) {
            List<ProductDTO> products = customerDTO.products().stream().map(myProductService::getCustomerProduct).toList();
            existingEntity.setUpdatedOn(LocalDateTime.now(Clock.systemDefaultZone()));
            updateExistingCustomerProduct(existingEntity, products);
            updateExistingCustomerSpecification(existingEntity, customerEntity.getCustomerSpecification(), products);
            return myCustomerMapper.mapToDTO(myCustomerRepository.saveAndFlush(existingEntity));
        }
        return myCustomerMapper.mapToDTO(myCustomerRepository.save(customerEntity));
    }

    private void updateExistingCustomerProduct(Customer existingCustomer, List<ProductDTO> updatableProduct) {
        List<CustomerProduct> existingProducts = existingCustomer.getProducts();

        List<CustomerProduct> collectAllProducts = new ArrayList<>();
        if (nonNull(existingProducts)) {
            collectAllProducts.addAll(existingProducts);
            collectAllProducts.addAll(updatableProduct.stream().map(x -> customerProuct(x, existingCustomer)).toList());
            List<CustomerProduct> selectedUniqueProducts = new ArrayList<>(collectAllProducts.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CustomerProduct::getProductId)))).stream().toList());
            existingCustomer.setProducts(selectedUniqueProducts);
        }
    }

    private void updateExistingCustomerSpecification(Customer existingCustomer, CustomerSpecification updatableCustomerSpecification, List<ProductDTO> updatableProduct) {

        CustomerSpecification customerSpecification = existingCustomer.getCustomerSpecification();
        updateCustomerSpecification(customerSpecification, updatableCustomerSpecification);
        updateCustomerProductSpecifications(existingCustomer, updatableProduct, updatableCustomerSpecification);
    }

    private CustomerProduct customerProuct(ProductDTO product, Customer existingCustomer) {
        return CustomerProduct.builder().productId(product.id()).productName(product.name()).customer(existingCustomer).build();
    }

    private void updateCustomerSpecification(CustomerSpecification customerSpecification, CustomerSpecification updatableCustomerSpecification) {
        if (nonNull(updatableCustomerSpecification)) {
            if (!Objects.equals(customerSpecification.getSpecificationName(), updatableCustomerSpecification.getSpecificationName())) {
                customerSpecification.setSpecificationName(updatableCustomerSpecification.getSpecificationName());
            }
            if (!Objects.equals(customerSpecification.getSpecificationFor(), updatableCustomerSpecification.getSpecificationFor())) {
                customerSpecification.setSpecificationFor(updatableCustomerSpecification.getSpecificationFor());
            }
            if (!Objects.equals(customerSpecification.getDescription(), updatableCustomerSpecification.getDescription())) {
                customerSpecification.setDescription(updatableCustomerSpecification.getDescription());
            }
            customerSpecification.setUpdatedOn(updatableCustomerSpecification.getUpdatedOn());
        }
    }

    private void updateCustomerProductSpecifications(Customer existingCustomer, List<ProductDTO> updatableProduct, CustomerSpecification customerSpecification) {
        if (nonNull(customerSpecification)) {
            CustomerSpecification existingCustomerSpecification = existingCustomer.getCustomerSpecification();
            List<CustomerProductSpecification> customerExistProductSpecifications = existingCustomerSpecification.getCustomerProductSpecifications();
            List<CustomerProductSpecification> customerUpdatableProductSpecifications = customerSpecification.getCustomerProductSpecifications();
            if (nonNull(customerUpdatableProductSpecifications)) {
                //merge with existing product specification
                customerUpdatableProductSpecifications.forEach(updatable -> customerExistProductSpecifications.stream().filter(x -> x.getProductId().equals(updatable.getProductId())).forEach(existing -> mergeProductSpecification(existing, updatable)));
                // create new product specification for new product
                customerUpdatableProductSpecifications.stream()
                        .filter(product -> existingCustomer.getProducts().stream().anyMatch(customerProduct -> customerProduct.getProductId().equals(product.getProductId())))
                        .filter(updatable -> customerExistProductSpecifications.stream().noneMatch(existing -> existing.getProductId().equals(updatable.getProductId()))).map(updatable -> createProductSpecification(updatable, existingCustomerSpecification)).forEach(customerExistProductSpecifications::add);
            }


            //                updatableProduct.stream().filter(x -> collectAllSpecificationProducts.stream().allMatch(p -> x.id().equals(p.getProductId()))).map(x -> createDefaultProductSpecification(customerSpecification, x)).forEach(customerExistProductSpecifications::add);

//                List<CustomerProductSpecification> selectedUniqueProducts = new ArrayList<>(collectAllSpecificationProducts.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CustomerProductSpecification::getProductId)))));
//                customerSpecification.setCustomerProductSpecifications(selectedUniqueProducts);
//            customerSpecification.setCustomer(existingCustomer);
//            existingCustomer.setCustomerSpecification(customerSpecification);
        }
    }

    private CustomerProductSpecification mergeProductSpecification(CustomerProductSpecification customerProductSpecification, CustomerProductSpecification matchProductSpecification) {
        if (matchProductSpecification.getProductId().equals(customerProductSpecification.getProductId())) {

            if (!Objects.equals(customerProductSpecification.getPrice(), matchProductSpecification.getPrice())) {
                customerProductSpecification.setPrice(matchProductSpecification.getPrice());

            }
            if (!Objects.equals(customerProductSpecification.getQuantity(), matchProductSpecification.getQuantity())) {
                customerProductSpecification.setQuantity(matchProductSpecification.getQuantity());

            }
            if (!Objects.equals(customerProductSpecification.getStartUnit(), matchProductSpecification.getStartUnit())) {
                customerProductSpecification.setStartUnit(matchProductSpecification.getStartUnit());

            }
            if (!Objects.equals(customerProductSpecification.getEndUnit(), matchProductSpecification.getEndUnit())) {
                customerProductSpecification.setEndUnit(matchProductSpecification.getEndUnit());
            }

        }

        return customerProductSpecification;

    }

    private CustomerProductSpecification createProductSpecification(CustomerProductSpecification updatable, CustomerSpecification customerSpec) {

        return CustomerProductSpecification.builder().productId(updatable.getProductId()).quantity(updatable.getQuantity()).unitOfMeasurement(updatable.getUnitOfMeasurement()).price(updatable.getPrice()).customerSpecification(customerSpec).build();
    }

    private CustomerProductSpecification createDefaultProductSpecification(CustomerSpecification systemDefault, ProductDTO dto) {
        return CustomerProductSpecification.builder().productId(dto.id()).quantity((float) dto.quantity()).unitOfMeasurement(dto.unitOfMeasurement().getUnitType()).price(dto.price()).customerSpecification(systemDefault).build();
    }

    @Override
    public CustomerDTO delete(String customerId, String msisdn) {
        Customer customer;
        if (customerId!=null) {
            customer = myCustomerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(AppEntity.ID, customerId));
        } else {
            customer = myCustomerRepository.findByMsisdn(msisdn);
        }
        myCustomerRepository.delete(customer);

        return myCustomerMapper.mapToDTO(customer);
    }

    private void setDefaultCustomerSpecification(Customer entity, List<ProductDTO> products) {
        CustomerSpecification customerSpecification = entity.getCustomerSpecification();
        if (isNull(customerSpecification)) {
            CustomerSpecification defaultSpecification = createDefaultSpecification(products);
            defaultSpecification.setCustomer(entity);
            entity.setCustomerSpecification(defaultSpecification);
        }
    }

    private CustomerSpecification createDefaultSpecification(List<ProductDTO> productDTOS) {


        CustomerSpecification systemDefault = CustomerSpecification.builder().specificationName("System Default").build();
        List<CustomerProductSpecification> customerProductSpecifications = new ArrayList<>();
        for (ProductDTO productDTO : productDTOS) {
            customerProductSpecifications.add(createDefaultProductSpecification(systemDefault, productDTO));
        }
        systemDefault.setCustomerProductSpecifications(customerProductSpecifications);

        return systemDefault;
    }

    @Override
    public Set<CustomerDTO> getAll(final String khatabookId) {
        return myCustomerRepository.findByKhatabookId(khatabookId).stream().map(myCustomerMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public CustomerDTO getCustomerByMsisdn(final String khatabookId, final String msisdn) {
        return myCustomerMapper.mapToDTO(myCustomerRepository.findByKhatabookIdAndMsisdn(khatabookId, msisdn));
    }

}
