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
public class NotebookItemsView implements Serializable {
    private List<NotebookItemView> item;
}
