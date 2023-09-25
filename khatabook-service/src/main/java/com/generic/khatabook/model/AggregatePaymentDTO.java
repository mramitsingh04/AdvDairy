package com.generic.khatabook.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AggregatePaymentDTO(String productId, LocalDateTime from, LocalDateTime to) {
    public AggregatePaymentDTO(LocalDateTime from, LocalDateTime to){
        this(null, from, to);

    }
  public AggregatePaymentDTO(LocalDate from, LocalDate to){
        this(null, LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX));
    }
    public AggregatePaymentDTO(String productId){
        this(productId, null, null);
    }


}
