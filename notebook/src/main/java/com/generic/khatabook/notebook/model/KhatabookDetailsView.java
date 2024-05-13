package com.generic.khatabook.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "customer")
@Relation(collectionRelation = "customers")
public final class KhatabookDetailsView extends RepresentationModel<KhatabookDetailsView> {
    private final String bookId;
    private final String khatabookId;
    private final int numberOfCustomers;
    private final Set<CustomerDTO> customers;
}
