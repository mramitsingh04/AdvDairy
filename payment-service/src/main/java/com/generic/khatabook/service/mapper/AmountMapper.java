package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.exceptions.IncompatibleStateException;
import com.generic.khatabook.model.AmountDTO;

import java.util.Currency;
import java.util.Locale;
public class AmountMapper {
    public static Amount add(final Amount amount, final Amount amount1) {
        if (!amount.unitOfMeasurement().equals(amount1.unitOfMeasurement())) {
            throw new IncompatibleStateException(amount.unitOfMeasurement() + " can't convert " + amount1.unitOfMeasurement() + ".");

        }
        return Amount.of(amount.unitValue().add(amount1.unitValue()), amount.unitOfMeasurement());
    }

    public static AmountDTO dto(final Amount amount) {
        return new AmountDTO(amount.unitValue(), amount.unitOfMeasurement(), Currency.getInstance(Locale.getDefault()));
    }

    public static AmountDTO add(final AmountDTO amount, final AmountDTO amount1) {
        if (!amount.currency().equals(amount1.currency())) {
            throw new IncompatibleStateException(amount.currency() + " can't convert " + amount1.currency() + ".");

        }
        return AmountDTO.of(amount.value().add(amount1.value()), amount.unitOfMeasurement(), amount.currency());
    }
}
