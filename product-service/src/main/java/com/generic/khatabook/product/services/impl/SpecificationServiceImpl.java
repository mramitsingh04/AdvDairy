package com.generic.khatabook.product.services.impl;

import com.generic.khatabook.product.repository.SpecificationRepository;
import com.generic.khatabook.product.services.SpecificationService;
import com.generic.khatabook.product.services.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.generic.khatabook.product.model.*;
@Service
public class SpecificationServiceImpl implements SpecificationService {

    private SpecificationRepository mySpecificationRepository;
    private SpecificationMapper mySpecificationMapper;

    @Autowired
    public SpecificationServiceImpl(final SpecificationRepository specificationManagementRepository, final SpecificationMapper specificationMapper) {
        mySpecificationRepository = specificationManagementRepository;
        mySpecificationMapper = specificationMapper;
    }

    @Override
    public SpecificationDTO addSpecification(final SpecificationDTO specification) {
        return mySpecificationMapper.mapToDTO(mySpecificationRepository.save(mySpecificationMapper.mapToEntity(specification)));
    }

    @Override
    public boolean findById(final String id) {
        return mySpecificationRepository.findById(id).isPresent();
    }

    @Override
    public SpecificationDTO find(final String id) {
        return mySpecificationRepository.findById(id).map(mySpecificationMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<SpecificationDTO> findAll() {
        return mySpecificationMapper.mapToDTOs(mySpecificationRepository.findAll());
    }

    @Override
    public void deleteSpecifications(final SpecificationDTO specification) {
        mySpecificationRepository.delete(mySpecificationMapper.mapToEntity(specification));
    }

    @Override
    public SpecificationDTO updateSpecification(final SpecificationDTO specificationDTO) {
        return mySpecificationMapper.mapToDTO(mySpecificationRepository.save(mySpecificationMapper.mapToEntity(specificationDTO)));
    }
}
