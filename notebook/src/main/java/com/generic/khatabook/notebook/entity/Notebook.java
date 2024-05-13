package com.generic.khatabook.notebook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notebook")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notebookId;
    private String bookId;
    private String customerId;
    private String khatabookId;
    private String description;
    @OneToMany(mappedBy = "fileId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<FileDetail> files;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;


}
