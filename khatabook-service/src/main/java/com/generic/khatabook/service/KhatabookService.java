package com.generic.khatabook.service;

import com.generic.khatabook.model.KhatabookDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface KhatabookService {
    boolean isValid(KhatabookDTO customer);

    KhatabookDTO get(String msisdn);

    void create(KhatabookDTO customer);

    KhatabookDTO update(KhatabookDTO customer);

    KhatabookDTO delete(String khatabookId, String msidn);

    List<KhatabookDTO> getAll();

    KhatabookDTO getKhatabookByKhatabookId(String khatabookId);
}
