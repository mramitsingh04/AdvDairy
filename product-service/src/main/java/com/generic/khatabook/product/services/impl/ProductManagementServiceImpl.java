package com.generic.khatabook.product.services.impl;

import com.generic.khatabook.product.exceptions.AppEntity;
import com.generic.khatabook.product.exceptions.IllegalArgumentException;
import com.generic.khatabook.product.exceptions.SubEntity;
import com.generic.khatabook.product.model.Container;
import com.generic.khatabook.product.model.ProductDTO;
import com.generic.khatabook.product.model.ProductUpdatable;
import com.generic.khatabook.product.model.UnitOfMeasurement;
import com.generic.khatabook.product.repository.ProductManagementRepository;
import com.generic.khatabook.product.repository.ProductRatingRepository;
import com.generic.khatabook.product.services.ProductManagementService;
import com.generic.khatabook.product.services.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductManagementServiceImpl implements ProductManagementService {

    private final ProductManagementRepository myProductManagementRepository;
    private final ProductRatingRepository myProductRatingRepository;
    private final ProductMapper myProductMapper;

    @Override
    public List<ProductDTO> findAllProducts() {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findAll());
    }

    @Override
    public List<ProductDTO> findProductByName(final String productName) {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findByName(productName));
    }

    @Override
    public ProductDTO saveProduct(final ProductDTO product) {

        return myProductMapper.mapToDTO(myProductManagementRepository.save(myProductMapper.mapToEntity(product)));
    }

    @Override
    public Container<ProductDTO, ProductUpdatable> findProductById(final String productId) {
        return myProductMapper.mapToContainer(myProductManagementRepository.findById(productId).orElse(null));
    }

    @Override
    public void delete(final ProductDTO productDTO) {
        myProductManagementRepository.delete(myProductMapper.mapToEntity(productDTO));
    }

    @Override
    public ProductDTO updateProduct(final ProductDTO product) {
        return myProductMapper.mapToDTO(myProductManagementRepository.save(myProductMapper.mapToEntity(product)));
    }

    @Override
    public List<ProductDTO> findProductByUnitOfMeasurement(final String unitOfMeasurement) {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findByUnitOfMeasurement(checkAndGetUnitOfMeasurement(unitOfMeasurement).getUnitType()));
    }

    private static UnitOfMeasurement checkAndGetUnitOfMeasurement(final String unitOfMeasurement) {
        final UnitOfMeasurement unitOfMeasurementR;
        try {
            unitOfMeasurementR = UnitOfMeasurement.valueOf(unitOfMeasurement.toUpperCase());
        } catch (java.lang.IllegalArgumentException e) {
            throw new IllegalArgumentException(AppEntity.PRODUCT, SubEntity.UNIT_OF_MEASUREMENT, unitOfMeasurement + " No enum constant found");
        }
        return unitOfMeasurementR;
    }
}
