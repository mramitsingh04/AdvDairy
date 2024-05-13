package com.generic.khatabook.service;

import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface KhatabookRestService {
    boolean isValid(KhatabookDTO customer);

    KhatabookView get(String msisdn);

    void create(KhatabookDTO customer);

    KhatabookView update(KhatabookDTO customer);

    KhatabookView delete(String khatabookId, String msisdn);

    List<KhatabookView> getAll();
    KhatabookView getKhatabookByKhatabookId(String khatabookId);

    boolean isExist(String khatabookId);

    List<KhatabookView> getAll(List<String> listOfGroupId);
}
