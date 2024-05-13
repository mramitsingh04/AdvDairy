package com.generic.khatabook.notebook.service;

import com.generic.khatabook.notebook.model.NotebookView;
import com.generic.khatabook.notebook.model.NotebookDTO;
import com.generic.khatabook.notebook.model.NotebookViews;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface NotebookRestService {
    boolean isValid(NotebookDTO customer);

    NotebookView get(final Long noteBookId);

    NotebookView create(NotebookDTO customer);

    NotebookView update(String khatabookId, String customerId, Long noteBookId, NotebookDTO customer);

    NotebookView delete(final Long noteBookId);

    List<NotebookView> getAll();
    NotebookView getNotebookByNotebookId(final Long noteBookId);

    boolean isExist(final Long noteBookId);

    NotebookViews findCustomerAllNoteBook(String khatabookId, String customerId);

    NotebookView findCustomerNoteBook(String khatabookId, String customerId, Long noteBookId);

    NotebookView saveAndStoreFiles(String khatabookId, String customerId, List<MultipartFile> files);
}
