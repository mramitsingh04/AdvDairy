package com.generic.khatabook.notebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Argument fount.")
public class NoPaymentForAggregateException extends RuntimeException {
    public NoPaymentForAggregateException(String s) {
        super(s);
    }

    public ProblemDetail get(){
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getMessage());
        problemDetail.setTitle("Resource not found.");
        return problemDetail;
    }

}
