package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "khatabook_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhatabookGroup {

    @Id
    private String groupId;
    private String groupName;
    private String groupDescription;
    private String status;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", referencedColumnName = "operatorId")
    private Operator operator;
    @OneToMany(mappedBy = "internalId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Khatabook> khatabooks;


}
