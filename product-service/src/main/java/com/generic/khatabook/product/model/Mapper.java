package com.generic.khatabook.product.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface Mapper<ENTITY, DTO, UPDATABLE> {

    default DTO mapToDTO(Optional<ENTITY> entityOpt) {

        return entityOpt.map(this::mapToDTO).orElse(null);
    }

    DTO mapToDTO(ENTITY entity);

    default List<ENTITY> mapToEntities(List<DTO> dtos) {
        if (Objects.isNull(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::mapToEntity).toList();
    }

    ENTITY mapToEntity(DTO dto);

    default List<DTO> mapToDTOs(final List<ENTITY> entities) {
        if (Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::mapToDTO).toList();
    }

    default Containers<DTO, UPDATABLE> mapToContainers(List<ENTITY> entities) {
        return new Containers<>(entities.stream().map(this::mapToContainer).toList());
    }

    Container<DTO, UPDATABLE> mapToContainer(ENTITY entity);

    default Containers<DTO, UPDATABLE> mapToContainers(ENTITY... entities) {

        final Containers<DTO, UPDATABLE> containers = new Containers<>();


        for (final ENTITY entity : entities) {

            containers.add(mapToContainer(entity));
        }

        return containers;
    }
}