package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.entity.Operator;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.KhatabookGroupDTO;
import com.generic.khatabook.model.Mapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class KhatabookGroupMapper implements Mapper<KhatabookGroup, KhatabookGroupDTO, Void> {


    private final KhatabookMapper khatabookMapper;

    public KhatabookGroupMapper(KhatabookMapper khatabookMapper) {
        this.khatabookMapper = khatabookMapper;
    }

    @Override
    public KhatabookGroup mapToEntity(final KhatabookGroupDTO myKhatabookGroup) {
        if (myKhatabookGroup == null) {
            return null;
        }
        return KhatabookGroup.builder().groupId(myKhatabookGroup.groupId()).groupName(myKhatabookGroup.groupName()).groupDescription(myKhatabookGroup.groupDescription()).operator(
                        Operator.builder().operatorId(myKhatabookGroup.operatorId()).build()).status(myKhatabookGroup.status())
                .build();
    }

    @Override
    public Container<KhatabookGroupDTO, Void> mapToContainer(final KhatabookGroup KhatabookGroup) {
        if (Objects.isNull(KhatabookGroup)) {
            return Container.empty();
        }

        final KhatabookGroupDTO KhatabookGroupDTO = mapToDTO(KhatabookGroup);

        return Container.of(KhatabookGroupDTO);
    }

    @Override
    public KhatabookGroupDTO mapToDTO(final KhatabookGroup entity) {
        return new KhatabookGroupDTO(entity.getGroupId(), entity.getGroupName(), entity.getGroupDescription(), entity.getOperator().getOperatorId(), entity.getStatus(), khatabookMapper.mapToDTOs(entity.getKhatabooks()));
    }

}
