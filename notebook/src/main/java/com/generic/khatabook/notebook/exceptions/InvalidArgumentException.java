package com.generic.khatabook.notebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Argument fount.")
public class InvalidArgumentException extends  RuntimeException {

    public InvalidArgumentException(final AppEntity appEntity, final String invalidMember) {
        super(appEntity.getName() + " don't have " + invalidMember + " member, invalid argument fount.!.");
    }

    public ProblemDetail get() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, super.getMessage());
    }


}
