package com.generic.khatabook.notebook.service.mapper;

import com.generic.khatabook.notebook.entity.GenerationDate;
import com.generic.khatabook.notebook.entity.Notebook;
import com.generic.khatabook.notebook.model.NotebookView;
import org.springframework.stereotype.Component;

@Component
public class NotebookViewMapper implements Mapper<Notebook, NotebookView, Void> {

    @Override
    public Notebook mapToEntity(final NotebookView khatabookDTO) {
        return mapToEntity(khatabookDTO, GenerationDate.creation());
    }

    @Override
    public Container<NotebookView, Void> mapToContainer(final Notebook khatabook) {
        return null;
    }

    public NotebookView mapToDTO(Notebook myNotebook) {

        if (myNotebook == null) {
            return null;
        }

        return NotebookView.builder()
                .bookId(myNotebook.getBookId())
                .customerId(myNotebook.getCustomerId())
                .notebookId(myNotebook.getNotebookId())
                .khatabookId(myNotebook.getKhatabookId())
                .createdOn(myNotebook.getCreatedOn())
                .updatedOn(myNotebook.getUpdatedOn())
                .deletedOn(myNotebook.getDeletedOn())
                .status(myNotebook.getStatus())
                .description(myNotebook.getDescription())
                .build();
    }

    public Notebook mapToEntity(NotebookView myNotebookView, final GenerationDate generationDate) {
        if (myNotebookView == null) {
            return null;
        }
        return Notebook.builder()
                .bookId(myNotebookView.getBookId())
                .customerId(myNotebookView.getCustomerId())
                .notebookId(myNotebookView.getNotebookId())
                .khatabookId(myNotebookView.getKhatabookId())
                .createdOn(generationDate.createdOn())
                .updatedOn(generationDate.updatedOn())
                .deletedOn(generationDate.deletedOn())
                .status(myNotebookView.getStatus())
                .description(myNotebookView.getDescription())
                .build();
    }
}
