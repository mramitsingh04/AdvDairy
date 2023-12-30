package com.generic.khatabook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "operators", uniqueConstraints = {
        @UniqueConstraint(name = "operator_email",
                columnNames = "emailId"
        ),
        @UniqueConstraint(name = "operator_msisdn",
                columnNames = "msisdn"
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operator {
    @Id
    private String operatorId;
    private String msisdn;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    @Email
    private String emailId;

//    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL)
    private List<KhatabookGroup> groups;

}
