package com.generic.khatabook.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NotebookDTO(Long noteBookId, String customerId, String khatabookId, String description,
                          LocalDateTime createdOn, String status) implements Serializable {

}



