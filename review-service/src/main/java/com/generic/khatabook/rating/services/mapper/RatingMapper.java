package com.generic.khatabook.rating.services.mapper;

import com.generic.khatabook.rating.entity.Rating;
import com.generic.khatabook.rating.model.Container;
import com.generic.khatabook.rating.model.Mapper;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class RatingMapper implements Mapper<Rating, RatingDTO, Void> {
    public Rating mapToEntity(RatingDTO dto) {
        return Rating.builder().id(dto.id()).customerId(dto.fromCustomerId()).productId(dto.entityId()).description(
                        dto.description()).rating(dto.rating())
                .build();
    }

    @Override
    public Container<RatingDTO, Void> mapToContainer(final Rating product) {

        if (Objects.isNull(product)) {
            return Container.empty();
        }

        return Container.of(mapToDTO(product));
    }


    @Override
    public RatingDTO mapToDTO(final Rating rating) {
        return new RatingDTO(rating.getId(), rating.getCustomerId(), RatingEntityType.valueOf(rating.getEntityType()),
                rating.getProductId(),
                rating.getRating(), rating.getDescription());
    }

    public List<Rating> mapToEntities(final List<RatingDTO> products) {
        if (Objects.isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToEntity).toList();
    }


}
