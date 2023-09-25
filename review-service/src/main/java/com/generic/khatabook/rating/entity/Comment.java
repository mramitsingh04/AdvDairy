package com.generic.khatabook.rating.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String commentId;
    @NotNull
    private String commentDetails;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
