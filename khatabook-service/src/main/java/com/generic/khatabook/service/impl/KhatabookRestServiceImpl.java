package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.model.GroupView;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookView;
import com.generic.khatabook.repository.KhatabookGroupRepository;
import com.generic.khatabook.service.KhatabookRestService;
import com.generic.khatabook.service.KhatabookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KhatabookRestServiceImpl implements KhatabookRestService {
    @Autowired
    KhatabookGroupRepository khatabookGroupRepository;
    @Autowired
    private KhatabookService myKhatabookService;

    @Override
    public boolean isValid(final KhatabookDTO khatabookDTO) {
        return myKhatabookService.isValid(khatabookDTO);
    }

    @Override
    public KhatabookView get(final String msisdn) {
        return getKhatabookView(myKhatabookService.get(msisdn));
    }

    @Override
    public void create(final KhatabookDTO khatabookDTO) {

        log.info("Khatabook {} created.", khatabookDTO.khatabookId());
        myKhatabookService.create(khatabookDTO);
        log.info("Khatabook {} successful created.", khatabookDTO.khatabookId());
    }

    @Override
    public KhatabookView update(final KhatabookDTO khatabookDTO) {
        log.info("Khatabook {} created.", khatabookDTO.khatabookId());
        myKhatabookService.update(khatabookDTO);

        log.info("Khatabook {} successful created.", khatabookDTO.khatabookId());
        return getKhatabookByKhatabookId(khatabookDTO.khatabookId());
    }

    @Override
    public KhatabookView delete(final String khatabookId, final String msisdn) {

        return getKhatabookView(myKhatabookService.delete(khatabookId, msisdn));
    }

    @Override
    public List<KhatabookView> getAll() {

        return myKhatabookService.getAll().stream().map(this::getKhatabookView).toList();
    }

    @Override
    public KhatabookView getKhatabookByKhatabookId(final String khatabookId) {
        return getKhatabookView(myKhatabookService.getKhatabookByKhatabookId(khatabookId));
    }

    private KhatabookView getKhatabookView(KhatabookDTO khatabookDTO) {
        KhatabookGroup group = getKhatabookGroup(khatabookDTO.groupId());
        return new KhatabookView(new GroupView(group.getGroupId(), group.getGroupName()), khatabookDTO.bookId(), khatabookDTO.khatabookId(), khatabookDTO.msisdn(), khatabookDTO.partnerName(), khatabookDTO.partnerDescription());
    }

    @Cacheable
    private KhatabookGroup getKhatabookGroup(String groupId) {
        return khatabookGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }


    @Override
    public boolean isExist(final String khatabookId) {

        return myKhatabookService.isExist(khatabookId);
    }

    @Override
    public List<KhatabookView> getAll(List<String> listOfGroupId) {
        return getAll().stream().filter(k -> listOfGroupId.contains(k.group().groupId())).toList();
    }
}
