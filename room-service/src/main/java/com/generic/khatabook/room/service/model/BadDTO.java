package com.generic.khatabook.room.service.model;

import java.util.List;

public record BadDTO(int id, boolean isEmpty, CustomerDTO customer, List<Product> products ) {
}
