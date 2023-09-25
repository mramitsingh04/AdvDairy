package com.generic.khatabook.service.mapper;

import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.Mapper;
import com.generic.khatabook.model.OperatorDTO;
import com.generic.khatabook.entity.Operator;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class OperatorMapper implements Mapper<Operator, OperatorDTO, Void> {

    @Override
    public Operator mapToEntity(final OperatorDTO myOperator) {
        if (myOperator == null) {
            return null;
        }
        return Operator.builder().operatorId(myOperator.operatorId()).firstName(myOperator.firstName()).lastName(myOperator.lastName()).msisdn(myOperator.msisdn()).emailId(myOperator.emailId()).build();
    }

    @Override
    public Container<OperatorDTO, Void> mapToContainer(final Operator Operator) {
        if (Objects.isNull(Operator)) {
            return Container.empty();
        }

        final OperatorDTO OperatorDTO = mapToDTO(Operator);

        return Container.of(OperatorDTO);
    }

    @Override
    public OperatorDTO mapToDTO(final Operator entity) {
        return new OperatorDTO(entity.getOperatorId(), entity.getFirstName(), entity.getLastName(), entity.getMsisdn(), entity.getEmailId());
    }

}
