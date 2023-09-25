package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "khatabooks")
public class Khatabook {

    @Id
    @GeneratedValue
    private Long internalId;
    private String bookId;
    private String khatabookId;
    private String msisdn;
    private String partnerName;
    private String partnerDescription;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "groupId")
    private KhatabookGroup khatabookGroup;


}
