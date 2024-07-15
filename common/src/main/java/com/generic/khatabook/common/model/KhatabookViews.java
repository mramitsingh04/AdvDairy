package com.generic.khatabook.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class KhatabookViews extends RepresentationModel<KhatabookViews> {
    private final List<KhatabookView> khatabookViews;

    public static KhatabookViews of(List<KhatabookView> all) {
        if (isNull(all)) {
            return null;
        }
        return new KhatabookViews(all);
    }
}
