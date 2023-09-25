package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_product_specifications")
@DynamicInsert
@DynamicUpdate
public class CustomerProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerProductSpecId;
    private String productId;
    private Float quantity;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_specification_id", referencedColumnName = "customerSpecificationId")
    @ToString.Exclude
    private CustomerSpecification customerSpecification;

    private Long startUnit;
    private Long endUnit;
    private String unitOfMeasurement;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
