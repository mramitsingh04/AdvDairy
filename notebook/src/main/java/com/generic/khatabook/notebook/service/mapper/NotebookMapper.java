package com.generic.khatabook.notebook.service.mapper;

import com.generic.khatabook.notebook.entity.GenerationDate;
import com.generic.khatabook.notebook.entity.Notebook;
import com.generic.khatabook.notebook.model.NotebookDTO;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class NotebookMapper implements Mapper<Notebook, NotebookDTO, Void> {

    @Override
    public Notebook mapToEntity(final NotebookDTO khatabookDTO) {
        return mapToEntity(khatabookDTO, GenerationDate.creation());
    }

    @Override
    public Container<NotebookDTO, Void> mapToContainer(final Notebook khatabook) {
        return null;
    }

    public NotebookDTO mapToDTO(Notebook myNotebook) {

        if (myNotebook == null) {
            return null;
        }
        return new NotebookDTO(myNotebook.getNotebookId(),myNotebook.getCustomerId(),myNotebook.getKhatabookId(),myNotebook.getDescription(), myNotebook.getCreatedOn(), myNotebook.getStatus());
    }

    public Notebook mapToEntity(NotebookDTO myNotebookDTO, final GenerationDate generationDate) {
        if (myNotebookDTO == null) {
            return null;
        }
        return Notebook.builder()
                .customerId(myNotebookDTO.customerId())
//                .notebookId(myNotebookDTO.noteBookId())
                .khatabookId(myNotebookDTO.khatabookId())
                .createdOn(generationDate.createdOn())
                .updatedOn(generationDate.updatedOn())
                .deletedOn(generationDate.deletedOn())
                .status("Y")
                .description(myNotebookDTO.description())
                .build();
    }
}
