package com.generic.khatabook.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(String customerId, String khatabookId, String msisdn,
                          String firstName, String lastName) {

}



