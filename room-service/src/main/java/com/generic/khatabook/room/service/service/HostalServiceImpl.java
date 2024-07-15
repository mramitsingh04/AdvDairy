package com.generic.khatabook.room.service.service;

import com.generic.khatabook.room.service.entity.Hostal;
import com.generic.khatabook.room.service.model.HostalDTO;
import com.generic.khatabook.room.service.repository.HostalRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public final class HostalServiceImpl implements HostalService {

    private final HostalRepository hostalRepository;

    public HostalServiceImpl(HostalRepository hostalRepository) {
        this.hostalRepository = hostalRepository;
    }

    @Override
    public List<HostalDTO> getAll() {
        return hostalRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public HostalDTO getHostalById(int id) {
        return hostalRepository.findById(id).map(this::toDto).orElse(null);
    }

    private HostalDTO toDto(Hostal hostal) {
        return new HostalDTO(hostal.getId(), hostal.getName(), Collections.emptyList());
    }

    @Override
    public List<HostalDTO> getHostalByName(String name) {
        return hostalRepository.findByName(name).stream().map(this::toDto).toList();
    }

    @Override
    public void saveOrUpdate(HostalDTO hostalDTO) {
        hostalRepository.save(toEntity(hostalDTO));
    }

    private Hostal toEntity(HostalDTO dto) {
        return new Hostal(dto.id(), dto.name(), Collections.emptyList());
    }

    @Override
    public void deleteHostal(int id) {
        hostalRepository.deleteById(id);
    }
}
