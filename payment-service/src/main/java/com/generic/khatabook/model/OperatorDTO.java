package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OperatorDTO(String operatorId, @NotNull String firstName, @NotNull String lastName,
                          @NotNull(message = "operator.msisdn.not.found") String msisdn,
                          @NotNull(message = "operator.id.not.found") String emailId) {
    public OperatorDTO copyOf(String operatorId) {
        return new OperatorDTO(operatorId, this.firstName, this.lastName, this.msisdn, this.emailId);
    }

}
