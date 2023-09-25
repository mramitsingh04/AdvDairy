package com.generic.khatabook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Argument fount.")
public class InvalidArgumentValueException extends RuntimeException {

    public InvalidArgumentValueException(final AppEntity appEntity, final String msg) {
        super(appEntity.getName() + " " + msg);
    }

    public InvalidArgumentValueException(final String s) {
        super(s);
    }

    public ProblemDetail get() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, super.getMessage());
    }


}
