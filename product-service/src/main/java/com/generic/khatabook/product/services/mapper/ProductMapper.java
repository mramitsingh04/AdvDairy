package com.generic.khatabook.product.services.mapper;

import com.generic.khatabook.product.model.Container;
import com.generic.khatabook.product.model.Mapper;
import com.generic.khatabook.product.model.ProductDTO;
import com.generic.khatabook.product.model.ProductUpdatable;
import com.generic.khatabook.product.model.UnitOfMeasurement;
import com.generic.khatabook.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
@Component
public class ProductMapper implements Mapper<Product, ProductDTO, ProductUpdatable> {
    public Product mapToEntity(ProductDTO dto) {
        return Product.builder().id(dto.id()).name(dto.name()).price(dto.price()).unitOfMeasurement(
                        dto.unitOfMeasurement().getUnitType())
                .quantity(dto.quantity() == 0 ? 1 : dto.quantity())
                .build();
    }

    @Override
    public Container<ProductDTO, ProductUpdatable> mapToContainer(final Product product) {

        if (Objects.isNull(product)) {
            return Container.empty();
        }

        final ProductDTO productDTO = mapToDTO(product);
        return Container.of(productDTO, productDTO.updatable());
    }


    @Override
    public ProductDTO mapToDTO(final Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice(),
                getUnitOfMeasurement(product), 0f);
    }

    private UnitOfMeasurement getUnitOfMeasurement(final Product product) {
        final UnitOfMeasurement dbValue;
        for (final UnitOfMeasurement value : UnitOfMeasurement.values()) {
            if (value.getUnitType().equals(product.getUnitOfMeasurement())) {
                return value;
            }
        }
        return UnitOfMeasurement.NONE;
    }

    public List<Product> mapToEntities(final List<ProductDTO> products) {
        if (Objects.isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToEntity).toList();
    }


}
