package com.generic.khatabook.rating.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private String commentId;
    @NotNull
    private String commentDetails;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}