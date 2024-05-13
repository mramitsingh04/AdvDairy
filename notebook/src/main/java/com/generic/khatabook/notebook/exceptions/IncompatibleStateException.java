package com.generic.khatabook.notebook.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class IncompatibleStateException extends RuntimeException {
    public IncompatibleStateException(final String msg) {
        super(msg);
    }
}
