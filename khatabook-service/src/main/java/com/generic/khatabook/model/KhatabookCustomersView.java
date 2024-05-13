package com.generic.khatabook.model;

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
public final class KhatabookCustomersView extends RepresentationModel<KhatabookCustomersView> {
    private final String bookId;
    private final String khatabookId;
    private final int numberOfCustomers;
    private final Set<CustomerDTO> customers;
    private final KhatabookPaymentSummaryView paymentSummary;
    private final CustomerSpecificationDTO customerSpecification;

    public KhatabookCustomersView(String bookId, String khatabookId, int numberOfCustomers, Set<CustomerDTO> customers, KhatabookPaymentSummaryView paymentSummary, CustomerSpecificationDTO customerSpecification) {
        this.bookId = bookId;
        this.khatabookId = khatabookId;
        this.numberOfCustomers = numberOfCustomers;
        this.customers = customers;
        this.paymentSummary = paymentSummary;
        this.customerSpecification = customerSpecification;
    }


    public KhatabookCustomersView(final KhatabookDTO khatabookDTO, final Set<CustomerDTO> customers) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), customers.size(), customers, KhatabookPaymentSummaryView.empty(), null);
    }

    public KhatabookCustomersView(final KhatabookDTO khatabookDTO, final Set<CustomerDTO> customers, final KhatabookPaymentSummaryView khatabookPaymentSummary) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), customers.size(), customers, khatabookPaymentSummary, null);
    }

    public KhatabookCustomersView(final KhatabookDTO khatabook, final CustomerDTO customer, final KhatabookPaymentSummaryView customerDairy, CustomerSpecificationDTO customerSpecification) {
        this(khatabook.bookId(), khatabook.khatabookId(), 1, Set.of(customer), customerDairy, customerSpecification);
    }


}
