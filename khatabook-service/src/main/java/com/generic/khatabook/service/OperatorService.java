package com.generic.khatabook.service;

import com.generic.khatabook.model.OperatorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface OperatorService {
    boolean isValid(OperatorDTO customer);

    OperatorDTO get(String msisdn);

    void create(OperatorDTO operator);

    OperatorDTO update(OperatorDTO operator);

    void delete(String operatorId);

    List<OperatorDTO> getAll();

    OperatorDTO getByOperatorId(String operatorId);

}
