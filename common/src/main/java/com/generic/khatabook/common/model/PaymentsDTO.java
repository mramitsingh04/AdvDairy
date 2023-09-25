package com.generic.khatabook.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class PaymentsDTO extends ArrayList<PaymentDTO> {

    public boolean hasAmounts() {
        return this.stream().map(PaymentDTO::products).flatMap(Collection::stream).map(CustomerProductDTO::amount).anyMatch(Objects::nonNull);
    }

    public AmountDTO finalAmount() {
        BinaryOperator<AmountDTO> addAmount = PaymentsDTO::add;
        return this.stream().map(PaymentDTO::products).flatMap(Collection::stream).map(CustomerProductDTO::amount).reduce(AmountDTO.ZERO, addAmount);
    }

    public static AmountDTO add(final AmountDTO amount, final AmountDTO amount1) {
        if (!amount.unitOfMeasurement().equals(amount1.unitOfMeasurement())) {
//            throw new IncompatibleStateException(amount.unitOfMeasurement() + " can't convert " + amount1.unitOfMeasurement() + ".");
//TODO fix me
        }
        return AmountDTO.of(amount.value().add(amount1.value()), amount.unitOfMeasurement());
    }

}
