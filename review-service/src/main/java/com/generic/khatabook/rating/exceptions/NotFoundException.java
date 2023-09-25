package com.generic.khatabook.rating.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not fount.")
public class NotFoundException extends RuntimeException {

    public NotFoundException(final AppEntity appEntity, final Long id) {
        this(appEntity, String.valueOf(id));
    }


    public NotFoundException(final AppEntity appEntity, final String id) {
        super(appEntity.getName() + " " + id + " not fount!.");
    }

    public NotFoundException(final AppEntity khatabook, final AppEntity customer, final String khatabookId,
                             final String customerId)
    {
        super(khatabook.getName() + " " + khatabookId + " and " + customer.getName() + " " + customerId + " not " +
                "fount!.");
    }

    public ProblemDetail get() {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, super.getMessage());
        problemDetail.setTitle("Resource not found.");
        return problemDetail;
    }


}
