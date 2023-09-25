package com.generic.khatabook.repository;

import com.generic.khatabook.entity.KhatabookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhatabookGroupRepository extends JpaRepository<KhatabookGroup, String> {
    Optional<KhatabookGroup> findByGroupName(String groupName);
}
