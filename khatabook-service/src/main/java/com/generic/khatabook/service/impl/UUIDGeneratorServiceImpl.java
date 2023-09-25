package com.generic.khatabook.service.impl;

import com.generic.khatabook.service.IdGeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGeneratorServiceImpl implements IdGeneratorService {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
