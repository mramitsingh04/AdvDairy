package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.AggregatePayment;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.Mapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class AggregatePaymentMapper implements Mapper<AggregatePayment, AggregatePaymentDTO, Void> {

    @Override
    public AggregatePayment mapToEntity(final AggregatePaymentDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return new AggregatePayment(dto.from(), dto.to());
    }

    @Override
    public Container<AggregatePaymentDTO, Void> mapToContainer(final AggregatePayment aggregatePayment) {
        return Container.of(mapToDTO(aggregatePayment));
    }

    @Override
    public AggregatePaymentDTO mapToDTO(final AggregatePayment aggregatePayment) {
        if (Objects.isNull(aggregatePayment)) {
            return null;
        }
        return new AggregatePaymentDTO(String.valueOf(aggregatePayment.getId()));
//        return new AggregatePaymentDTO(aggregatePayment.getProductId(), aggregatePayment.getStartDate(), aggregatePayment.getEndDate());
    }

    public AggregatePayment mapToEntity(final AggregatePaymentDTO dto, final CustomerDTO customer) {

        return AggregatePayment.builder().build();
//        return AggregatePayment.builder().customerId(customer.customerId()).khatabookId(customer.khatabookId()).startDate(
//                dto.from()).endDate(dto.to()).productId(dto.productId()).build();
    }

}
