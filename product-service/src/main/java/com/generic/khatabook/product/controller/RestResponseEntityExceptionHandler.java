package com.generic.khatabook.product.controller;

import com.generic.khatabook.product.exceptions.DuplicateFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateFoundException.class)
    public ResponseEntity<ProblemDetail> duplicateEntity(DuplicateFoundException duplicateFoundException) {


        return ResponseEntity.of(duplicateFoundException.get()).build();
    }

}
