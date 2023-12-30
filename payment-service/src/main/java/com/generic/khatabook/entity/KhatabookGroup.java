package com.generic.khatabook.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
