package com.generic.khatabook.common.model;

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
    private final KhatabookPaymentSummaryView paymentSummary;
    private final CustomerSpecificationDTO customerSpecification;

    public KhatabookCustomerView(String bookId, String khatabookId, int numberOfCustomers, CustomerDTO customer, KhatabookPaymentSummaryView paymentSummary, CustomerSpecificationDTO customerSpecification) {
        this.bookId = bookId;
        this.khatabookId = khatabookId;
        this.numberOfCustomers = numberOfCustomers;
        this.customer = customer;
        this.paymentSummary = paymentSummary;
        this.customerSpecification = customerSpecification;
    }


    public KhatabookCustomerView(final KhatabookDTO khatabookDTO, final  CustomerDTO customer) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), 1, customer, KhatabookPaymentSummaryView.empty(), null);
    }

    public KhatabookCustomerView(final KhatabookDTO khatabookDTO, final  CustomerDTO customer, final KhatabookPaymentSummaryView khatabookPaymentSummary) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), 1, customer, khatabookPaymentSummary, null);
    }

    public KhatabookCustomerView(final KhatabookDTO khatabook, final CustomerDTO customer, final KhatabookPaymentSummaryView customerDairy, CustomerSpecificationDTO customerSpecification) {
        this(khatabook.bookId(), khatabook.khatabookId(), 1, customer, customerDairy, customerSpecification);
    }


}
