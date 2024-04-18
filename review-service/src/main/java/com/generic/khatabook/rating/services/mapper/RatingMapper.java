package com.generic.khatabook.rating.services.mapper;

import com.generic.khatabook.rating.entity.Rating;
import com.generic.khatabook.rating.model.Container;
import com.generic.khatabook.rating.model.Mapper;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class RatingMapper implements Mapper<Rating, RatingDTO, Void> {
    public Rating mapToEntity(RatingDTO dto) {
        return Rating.builder().id(dto.id()).customerId(dto.customerId())
                .entityType(dto.entityType().name())
                .entityId(dto.entityId()).description(dto.description()).rating(dto.rating()).build();
    }

    @Override
    public Container<RatingDTO, Void> mapToContainer(final Rating product) {

        if (isNull(product)) {
            return Container.empty();
        }

        return Container.of(mapToDTO(product));
    }


    @Override
    public RatingDTO mapToDTO(final Rating rating) {
        if (isNull(rating)) {
            return null;
        }
        return new RatingDTO(rating.getId(), rating.getCustomerId(), RatingEntityType.valueOf(rating.getEntityType()), rating.getEntityId(), rating.getRating(), rating.getDescription());
    }

    public List<Rating> mapToEntities(final List<RatingDTO> products) {
        if (isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToEntity).toList();
    }


}
