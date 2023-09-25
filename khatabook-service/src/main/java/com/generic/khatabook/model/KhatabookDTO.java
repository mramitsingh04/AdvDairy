package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record KhatabookDTO(String groupId, String bookId,
                           @NotNull(message = "khatabook.id.not.found") String khatabookId,
                           @NotNull(message = "khatabook.msisdn.not.found") String msisdn, String partnerName,
                           String partnerDescription) {


    public KhatabookDTO copyOf(String bookId) {
        return new KhatabookDTO(groupId, bookId, this.khatabookId, this.msisdn, this.partnerName, this.partnerDescription);
    }

    public KhatabookDTO copyOf(String groupId, String bookId) {
        return new KhatabookDTO(groupId, bookId, this.khatabookId, this.msisdn, this.partnerName, this.partnerDescription);
    }

}
