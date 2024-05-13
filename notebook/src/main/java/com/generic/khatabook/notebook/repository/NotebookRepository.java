package com.generic.khatabook.notebook.repository;

import com.generic.khatabook.notebook.entity.Notebook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findByKhatabookIdAndCustomerId(String khatabookId, String customerId);
    Notebook findByKhatabookIdAndCustomerIdAndNotebookId(String khatabookId, String customerId, Long noteBookId);
}
