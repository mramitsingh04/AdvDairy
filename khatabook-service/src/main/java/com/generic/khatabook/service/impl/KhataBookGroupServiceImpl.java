package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.model.KhatabookGroupDTO;
import com.generic.khatabook.repository.KhatabookGroupRepository;
import com.generic.khatabook.service.KhataBookGroupService;
import com.generic.khatabook.service.mapper.KhatabookGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KhataBookGroupServiceImpl implements KhataBookGroupService {

    private final KhatabookGroupRepository khatabookGroupRepository;
    private final KhatabookGroupMapper myKhatabookGroupMapper;

    @Autowired
    public KhataBookGroupServiceImpl(KhatabookGroupRepository khatabookGroupRepository, KhatabookGroupMapper myKhatabookGroupMapper) {
        this.khatabookGroupRepository = khatabookGroupRepository;
        this.myKhatabookGroupMapper = myKhatabookGroupMapper;
    }

    @Override
    public boolean isValid(KhatabookGroupDTO khatabookGroupDTO) {
        return khatabookGroupRepository.exists(Example.of(KhatabookGroup.builder().groupId(khatabookGroupDTO.groupId())
                .build()));
    }

    @Override
    public KhatabookGroupDTO get(String groupId) {
        return myKhatabookGroupMapper.mapToDTO(khatabookGroupRepository.findById(groupId));
    }

    @Override
    public void create(KhatabookGroupDTO KhatabookGroup) {

        update(KhatabookGroup);

    }

    @Override
    public KhatabookGroupDTO update(KhatabookGroupDTO khatabookGroupDTO) {
        return myKhatabookGroupMapper.mapToDTO(khatabookGroupRepository.save(myKhatabookGroupMapper.mapToEntity(khatabookGroupDTO)));
    }

    @Override
    public void delete(String groupId) {
        khatabookGroupRepository.deleteById(groupId);
    }

    @Override
    public List<KhatabookGroupDTO> getAll() {
        return myKhatabookGroupMapper.mapToDTOs(khatabookGroupRepository.findAll());
    }

    @Override
    public List<KhatabookGroupDTO> getAllGroups(String[] groups) {
        return getAll().stream().filter(g -> Arrays.asList(groups).contains(g.groupName())).collect(Collectors.toList());
    }
}
