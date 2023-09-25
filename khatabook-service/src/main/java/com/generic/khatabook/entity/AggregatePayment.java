package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "aggregate_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregatePayment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    private String customerId;
    private String khatabookId;
    private String productId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    @CreationTimestamp
    private LocalDateTime createdOn;

    public AggregatePayment(final LocalDateTime from, final LocalDateTime to) {

    }
}