package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.GenerationDate;
import com.generic.khatabook.entity.Khatabook;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.repository.KhatabookGroupRepository;
import com.generic.khatabook.repository.KhatabookRepository;
import com.generic.khatabook.service.KhatabookService;
import com.generic.khatabook.service.mapper.KhatabookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;
@Service
@Slf4j
public class KhatabookServiceImpl implements KhatabookService {
    @Autowired
    private KhatabookRepository myKhatabookRepository;
    @Autowired
    private KhatabookMapper myKhatabookMapper;
    @Autowired
    private KhatabookGroupRepository khatabookGroupRepository;

    @Override
    public boolean isValid(final KhatabookDTO khatabookDTO) {
        return myKhatabookRepository.exists(Example.of(Khatabook.builder().khatabookId(khatabookDTO.khatabookId()).build()));
    }

    @Override
    public KhatabookDTO get(final String msisdn) {
        return myKhatabookMapper.mapToDTO(myKhatabookRepository.findByMsisdn(msisdn).orElse(null));
    }

    @Override
    public void create(final KhatabookDTO khatabookDTO) {

        log.info("Khatabook {} created.", khatabookDTO.khatabookId());
        Khatabook entity = myKhatabookMapper.mapToEntity(khatabookDTO, GenerationDate.creation());
        entity.setKhatabookGroup(khatabookGroupRepository.findById(khatabookDTO.groupId()).orElse(null));
        myKhatabookRepository.save(entity);
        log.info("Khatabook {} successful created.", khatabookDTO.khatabookId());
    }

    @Override
    public KhatabookDTO update(final KhatabookDTO khatabookDTO) {
        log.info("Khatabook {} created.", khatabookDTO.khatabookId());
        myKhatabookRepository.save(myKhatabookMapper.mapToEntity(khatabookDTO, GenerationDate.modification()));

        log.info("Khatabook {} successful created.", khatabookDTO.khatabookId());
        return getKhatabookByKhatabookId(khatabookDTO.khatabookId());
    }

    @Override
    public KhatabookDTO delete(final String khatabookId, final String msisdn) {
        Khatabook customer;
        if (isNull(khatabookId) && isNull(msisdn)) {
            final Khatabook foundLastKhatabook = myKhatabookRepository.findAll().stream().sorted(Comparator.comparing(
                    Khatabook::getCreatedOn)).findFirst().orElseThrow(() -> new IllegalArgumentException(
                    "all value is deleted"));
            myKhatabookRepository.delete(foundLastKhatabook);
            return myKhatabookMapper.mapToDTO(foundLastKhatabook);
        }

        if (khatabookId != null) {
            customer = myKhatabookRepository.findByKhatabookId(khatabookId).orElseThrow(() -> new NotFoundException(
                    AppEntity.KHATABOOK,
                    khatabookId));
        } else {
            customer = myKhatabookRepository.findByMsisdn(msisdn).orElse(null);
        }
        myKhatabookRepository.delete(customer);

        return myKhatabookMapper.mapToDTO(customer);
    }

    @Override
    public List<KhatabookDTO> getAll() {

        return myKhatabookRepository.findAll().stream().map(myKhatabookMapper::mapToDTO).toList();
    }

    @Override
    public KhatabookDTO getKhatabookByKhatabookId(final String khatabookId) {
        final Khatabook myKhatabook = myKhatabookRepository.findByKhatabookId(khatabookId).stream().findFirst().orElse(
                null);
        return myKhatabookMapper.mapToDTO(myKhatabook);
    }

    @Override
    public boolean isExist(final String khatabookId) {

        return myKhatabookRepository.findByKhatabookId(khatabookId).isPresent();
    }
}
