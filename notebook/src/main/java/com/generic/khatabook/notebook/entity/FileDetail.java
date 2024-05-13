package com.generic.khatabook.notebook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item_file")
public class FileDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    private String filePath;
    private String fileName;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

}
