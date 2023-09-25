package com.generic.khatabook.service.impl;

import com.generic.khatabook.model.OperatorDTO;
import com.generic.khatabook.entity.Operator;
import com.generic.khatabook.repository.OperatorRepository;
import com.generic.khatabook.service.OperatorService;
import com.generic.khatabook.service.mapper.OperatorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository myOperatorRepository;
    private final OperatorMapper myOperatorMapper;

    @Autowired
    public OperatorServiceImpl(OperatorRepository myOperatorRepository, OperatorMapper myOperatorMapper) {
        this.myOperatorRepository = myOperatorRepository;
        this.myOperatorMapper = myOperatorMapper;
    }

    @Override
    public boolean isValid(OperatorDTO operator) {
        return myOperatorRepository.exists(Example.of(Operator.builder().operatorId(operator.operatorId())
                .build()));
    }

    @Override
    public OperatorDTO get(String msisdn) {
        return myOperatorMapper.mapToDTO(myOperatorRepository.findByMsisdn(msisdn));
    }

    @Override
    public void create(OperatorDTO operator) {

        update(operator);

    }

    @Override
    public OperatorDTO update(OperatorDTO operator) {
        return myOperatorMapper.mapToDTO(myOperatorRepository.save(myOperatorMapper.mapToEntity(operator)));
    }

    @Override
    public void delete(String operatorId) {
        myOperatorRepository.deleteById(operatorId);
    }

    @Override
    public List<OperatorDTO> getAll() {
        return myOperatorMapper.mapToDTOs(myOperatorRepository.findAll());
    }

    @Override
    public OperatorDTO getByOperatorId(String operatorId) {
        return myOperatorMapper.mapToDTO(myOperatorRepository.findById(operatorId));
    }
}
