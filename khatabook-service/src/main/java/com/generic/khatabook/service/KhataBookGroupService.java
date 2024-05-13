package com.generic.khatabook.service;

import com.generic.khatabook.model.KhatabookGroupDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface KhataBookGroupService {
    boolean isValid(KhatabookGroupDTO khatabookGroupDTO);

    KhatabookGroupDTO get(String groupId);

    void create(KhatabookGroupDTO operator);

    KhatabookGroupDTO update(KhatabookGroupDTO khatabookGroupDTO);

    void delete(String groupId);

    List<KhatabookGroupDTO> getAll();

    List<KhatabookGroupDTO> getAllGroups(String[] groups);

}
