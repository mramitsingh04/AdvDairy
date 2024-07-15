package com.generic.khatabook.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record KhatabookView(GroupView group, String bookId,
                            @NotNull(message = "khatabook.id.not.found") String khatabookId,
                            @NotNull(message = "khatabook.msisdn.not.found") String msisdn, String partnerName,
                            String partnerDescription) {
}
