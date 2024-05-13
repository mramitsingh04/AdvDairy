package com.generic.khatabook.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "customer")
@Relation(collectionRelation = "customers")
public final class KhatabookCustomerView extends RepresentationModel<KhatabookCustomerView> {
    private final String bookId;
    private final String khatabookId;
    private final int numberOfCustomers;
    private final CustomerDTO customer;

    public String getFullName() {
        return getCustomer().firstName() + " " + getCustomer().lastName();
    }
}
