package com.generic.khatabook.product.services.impl;

import com.generic.khatabook.product.repository.SpecificationManagementRepository;
import com.generic.khatabook.product.services.SpecificationManagementService;
import com.generic.khatabook.product.services.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.generic.khatabook.product.model.*;
@Service
public class SpecificationManagementServiceImpl implements SpecificationManagementService {

    private SpecificationManagementRepository mySpecificationManagementRepository;
    private SpecificationMapper mySpecificationMapper;

    @Autowired
    public SpecificationManagementServiceImpl(final SpecificationManagementRepository specificationManagementRepository, final SpecificationMapper specificationMapper) {
        mySpecificationManagementRepository = specificationManagementRepository;
        mySpecificationMapper = specificationMapper;
    }

    @Override
    public SpecificationDTO addSpecification(final SpecificationDTO specification) {
        return mySpecificationMapper.mapToDTO(mySpecificationManagementRepository.save(mySpecificationMapper.mapToEntity(specification)));
    }

    @Override
    public boolean findById(final String id) {
        return mySpecificationManagementRepository.findById(id).isPresent();
    }

    @Override
    public SpecificationDTO find(final String id) {
        return mySpecificationManagementRepository.findById(id).map(mySpecificationMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<SpecificationDTO> findAll() {
        return mySpecificationMapper.mapToDTOs(mySpecificationManagementRepository.findAll());
    }

    @Override
    public void deleteSpecifications(final SpecificationDTO specification) {
        mySpecificationManagementRepository.delete(mySpecificationMapper.mapToEntity(specification));
    }

    @Override
    public SpecificationDTO updateSpecification(final SpecificationDTO specificationDTO) {
        return mySpecificationMapper.mapToDTO(mySpecificationManagementRepository.save(mySpecificationMapper.mapToEntity(specificationDTO)));
    }
}
