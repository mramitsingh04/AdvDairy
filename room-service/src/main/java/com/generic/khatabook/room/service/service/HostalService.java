package com.generic.khatabook.room.service.service;

import com.generic.khatabook.room.service.model.HostalDTO;

import java.util.List;

public sealed interface HostalService  permits HostalServiceImpl  {
    List<HostalDTO> getAll();
    HostalDTO getHostalById(int id);
    List<HostalDTO> getHostalByName(String name);
    void saveOrUpdate(HostalDTO hostalDTO);
    void deleteHostal(int id);
}
