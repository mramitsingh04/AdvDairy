package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.generic.khatabook.model.KhatabookDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record KhatabookGroupDTO(String groupId, @NotNull String groupName, @NotNull String groupDescription,
                                @NotNull(message = "operator.msisdn.not.found") String operatorId,
                                @NotNull(message = "operator.id.not.found") String status,
                                List<KhatabookDTO> khatabooks) {

}
