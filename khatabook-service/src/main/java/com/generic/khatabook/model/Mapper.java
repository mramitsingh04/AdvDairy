package com.generic.khatabook.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public interface Mapper<ENTITY, DTO, UPDATABLE> {

    ENTITY mapToEntity(DTO dto);

    Container<DTO, UPDATABLE> mapToContainer(ENTITY entity);

    DTO mapToDTO(ENTITY entity);

    default DTO mapToDTO(Optional<ENTITY> entityOpt) {

        return entityOpt.map(this::mapToDTO).orElse(null);
    }

    default List<ENTITY> mapToEntities(List<DTO> dtos) {
        if (isNull(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::mapToEntity).toList();
    }

    default List<DTO> mapToDTOs(final List<ENTITY> entities) {
        if (isNull(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::mapToDTO).toList();
    }

    default Containers<DTO, UPDATABLE> mapToContainers(List<ENTITY> entities) {
        return new Containers<>(entities.stream().map(this::mapToContainer).toList());
    }

    default Containers<DTO, UPDATABLE> mapToContainers(ENTITY... entities) {

        final Containers<DTO, UPDATABLE> containers = new Containers<>();
        if (isNull(entities)) {
            return Containers.empty();
        }

        for (final ENTITY entity : entities) {

            containers.add(mapToContainer(entity));
        }

        return containers;
    }
}