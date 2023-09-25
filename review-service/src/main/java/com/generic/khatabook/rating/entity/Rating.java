package com.generic.khatabook.rating.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String customerId;
    private String productId;
    private float rating;
    private String description;


}
