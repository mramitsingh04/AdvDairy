package com.generic.khatabook.common.model;

import java.util.Collections;
import java.util.List;

public record PaymentDTO(String to, String from, List<CustomerProductDTO> products) {
    public static PaymentDTO nullOf() {
        return null;
    }

    public PaymentDTO(String to, String from, CustomerProductDTO product) {
        this(to, from, Collections.singletonList(product));
    }
}
