package com.generic.khatabook.room.service.repository;

import com.generic.khatabook.room.service.entity.Hostal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostalRepository extends MongoRepository<Hostal, Integer> {
    long deleteByIdByIdBetween(int idStart, int idEnd);

    List<Hostal> findByName(String name);

}