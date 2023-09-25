package com.generic.khatabook.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class IllegalArgumentException extends RuntimeException {

    public IllegalArgumentException(final AppEntity appEntity, SubEntity entity, final String msg) {
        super(appEntity.getName() + "." + entity.getName() + " " + msg);
    }

    public IllegalArgumentException(final AppEntity appEntity, final Long id) {
        this(appEntity, String.valueOf(id));
    }

    public IllegalArgumentException(final AppEntity appEntity, final String id) {
        super(appEntity.getName() + " " + id + " not fount!.");
    }

    public IllegalArgumentException(final AppEntity khatabook, final AppEntity customer, final String khatabookId,
                                    final String customerId)
    {
        super(khatabook.getName() + " " + khatabookId + " and " + customer.getName() + " " + customerId + " not " +
                "fount!.");
    }

    public ProblemDetail get() {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getMessage());
        problemDetail.setTitle("Resource not found.");
        return problemDetail;
    }

}
