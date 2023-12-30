package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.model.CustomerProductSpecificationUpdatable;
import com.generic.khatabook.model.Mapper;
import com.generic.khatabook.model.UnitOfMeasurement;
import com.generic.khatabook.model.UnitOfValue;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
@Component
public class CustomerProductSpecificationMapper implements Mapper<CustomerProductSpecification, CustomerProductSpecificationDTO, CustomerProductSpecificationUpdatable> {
    @Override
    public CustomerProductSpecification mapToEntity(final CustomerProductSpecificationDTO dto) {
        return CustomerProductSpecification.builder()
                .customerProductSpecId(dto.id())
                .productId(dto.productId())
                .quantity(dto.quantity())
                .unitOfMeasurement(dto.unitOfMeasurement().getUnitType())
                .price(Objects.isNull(dto.unitOfValue()) ? BigDecimal.ZERO : dto.unitOfValue().price())
                .startUnit(Objects.isNull(dto.unitOfValue()) || Objects.isNull(dto.unitOfValue().start()) ? 0 : dto.unitOfValue().start())
                .endUnit(Objects.isNull(dto.unitOfValue()) || Objects.isNull(dto.unitOfValue().end()) ? 0 : dto.unitOfValue().end())
                .build();
    }

    @Override
    public CustomerProductSpecificationDTO mapToDTO(final CustomerProductSpecification entity) {
        return new CustomerProductSpecificationDTO(entity.getCustomerProductSpecId(), entity.getProductId(), entity.getQuantity(),
                new UnitOfValue(entity.getPrice(), entity.getStartUnit(),
                        entity.getEndUnit()),
                getUnitOfMeasurement(entity.getUnitOfMeasurement())
        );
    }

    private UnitOfMeasurement getUnitOfMeasurement(final String unitOfMeasurment) {
        final UnitOfMeasurement dbValue;
        for (final UnitOfMeasurement value : UnitOfMeasurement.values()) {
            if (value.getUnitType().equals(unitOfMeasurment)) {
                return value;
            }
        }
        return UnitOfMeasurement.NONE;
    }

    @Override
    public Container<CustomerProductSpecificationDTO, CustomerProductSpecificationUpdatable> mapToContainer(final CustomerProductSpecification entity) {

        if (Objects.isNull(entity)) {
            return Container.empty();
        }

        final CustomerProductSpecificationDTO dto = mapToDTO(entity);

        return Container.of(dto, dto.updatable());
    }
}
