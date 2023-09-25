package com.generic.khatabook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Acceptable.")
public class DuplicateFoundException extends RuntimeException {

    public DuplicateFoundException(final AppEntity appEntity, final String id) {
        super(appEntity.getName() + " " + id + " already found!.");
    }

    public ProblemDetail get(){
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getMessage());
        problemDetail.setTitle("Resource already found.");
        return problemDetail;
    }


}
