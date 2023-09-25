package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customer_products")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CustomerProduct {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "cus_prod_seq", sequenceName = "CUS_PROD_SEQ", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cus_prod_seq")
    private Long Id;
    @Transient
    private String productName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;
    private String productId;

    public String getHumanReadableName() {
        return productName + " (" + productId + ")";
    }

}
