package com.generic.khatabook.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class NotebookItemView implements Serializable {
    private Long notebookId;
    private String bookId;
    private String description;
    private List<String> filePaths;
    private String status;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
}
