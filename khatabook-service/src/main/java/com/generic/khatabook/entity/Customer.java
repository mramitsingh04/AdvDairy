package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Getter(AccessLevel.NONE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;
    private String khatabookId;
    private String msisdn;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_specification_id")
    @ToString.Exclude
    private CustomerSpecification customerSpecification;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CustomerProduct> products;

}
