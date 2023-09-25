package com.generic.khatabook.service.mapper;

import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.model.Mapper;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
@Component
public class CustomerSpecificationMapper implements Mapper<CustomerSpecification, CustomerSpecificationDTO, CustomerSpecificationUpdatable> {


    private final CustomerProductSpecificationMapper myCustomerProductSpecificationMapper;

    @Autowired(required = false)
    public CustomerSpecificationMapper(final CustomerProductSpecificationMapper myCustomerProductSpecificationMapper) {
        this.myCustomerProductSpecificationMapper = myCustomerProductSpecificationMapper;
    }


    @Override
    public CustomerSpecification mapToEntity(final CustomerSpecificationDTO dto) {

        if (isNull(dto)) {
            return null;
        }
        List<CustomerProductSpecification> allCustomerProductSpecification = Optional.of(dto).map(CustomerSpecificationDTO::products).stream().flatMap(Collection::stream).map(myCustomerProductSpecificationMapper::mapToEntity).collect(Collectors.toList());
        return CustomerSpecification.builder()
                .customerSpecificationId(dto.id())
                .specificationName(dto.name())
                .specificationFor(dto.specificationFor())
                .createdOn(dto.createdOn())
                .updatedOn(dto.updateOn())
                .deletedOn(dto.deleteOn())
                .customerProductSpecifications(allCustomerProductSpecification)
                .build();
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> mapToContainer(final CustomerSpecification customerSpecification) {
        if (isNull(customerSpecification)) {
            return Container.empty();
        }
        final CustomerSpecificationDTO dto = mapToDTO(customerSpecification);
        return Container.of(dto, dto.updatable());
    }

    @Override
    public CustomerSpecificationDTO mapToDTO(final CustomerSpecification customerSpecification) {
        if (isNull(customerSpecification)) {
            return null;
        }
        return new CustomerSpecificationDTO(customerSpecification.getCustomerSpecificationId(),
                customerSpecification.getSpecificationName(),
                customerSpecification.getDescription(),
                customerSpecification.getVersion(),
                customerSpecification.getSpecificationFor(),
                myCustomerProductSpecificationMapper.mapToDTOs(customerSpecification.getCustomerProductSpecifications()),
                customerSpecification.getCreatedOn(),
                customerSpecification.getUpdatedOn(),
                customerSpecification.getDeletedOn());
    }

}
