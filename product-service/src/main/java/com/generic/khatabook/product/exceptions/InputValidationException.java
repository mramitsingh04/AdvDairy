package com.generic.khatabook.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InputValidationException extends RuntimeException {

    public InputValidationException(final AppEntity appEntity, SubEntity entity, final String msg) {
        super(appEntity.getName() + "." + entity.getName() + " " + msg);
    }

    public InputValidationException(final AppEntity appEntity, final Long id) {
        this(appEntity, String.valueOf(id));
    }

    public InputValidationException(final AppEntity appEntity, final String id) {
        super(appEntity.getName() + " " + id + " not fount!.");
    }

    public InputValidationException(final AppEntity khatabook, final AppEntity customer, final String khatabookId,
                                    final String customerId)
    {
        super(khatabook.getName() + " " + khatabookId + " and " + customer.getName() + " " + customerId + " not " +
                "fount!.");
    }

    public ProblemDetail get() {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getMessage());
        problemDetail.setTitle("Data not valid.");
        problemDetail.setType(URI.create("GET"));
        return problemDetail;
    }

}
