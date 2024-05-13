package com.generic.khatabook.notebook.service;

import com.generic.khatabook.notebook.entity.Notebook;
import com.generic.khatabook.notebook.model.NotebookDTO;
import com.generic.khatabook.notebook.model.NotebookView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NotebookService {
    boolean isValid(NotebookDTO customer);

    NotebookDTO get(Long noteBookId);

    NotebookDTO create(NotebookDTO customer);

    NotebookDTO update(NotebookDTO customer);

    NotebookDTO delete(final Long noteBookId);

    List<NotebookDTO> getAll();
    NotebookDTO getNotebookByNotebookId(final Long noteBookId);

    boolean isExist(final Long noteBookId);

    List<NotebookDTO> findCustomerAllNoteBook(String khatabookId, String customerId);

    NotebookDTO findCustomerNoteBook(String khatabookId, String customerId, Long noteBookId);
}
