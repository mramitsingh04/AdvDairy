package com.generic.khatabook.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public record AmountDTO(BigDecimal value, String unitOfMeasurement, Currency currency) {

    public static final Currency DEFAULT = Currency.getInstance(Locale.getDefault());
    public static final AmountDTO ZERO = new AmountDTO(BigDecimal.ZERO);
    public static final AmountDTO TEN = new AmountDTO(BigDecimal.TEN);
    private static final Map<BigDecimal, AmountDTO> map = new HashMap<>();


    public AmountDTO() {
        this(BigDecimal.ZERO, DEFAULT.getCurrencyCode(), DEFAULT);
    }

    public AmountDTO(final BigDecimal amount) {
        this(amount, DEFAULT.getCurrencyCode(), DEFAULT);
    }

    public static AmountDTO of(BigDecimal amountValue, String unitOfMeasurement) {
        if (map.containsKey(amountValue)) {
            return map.get(amountValue);
        }
        return new AmountDTO(amountValue, unitOfMeasurement.toUpperCase(Locale.getDefault()), DEFAULT);
    }

    public static AmountDTO of(BigDecimal amountValue, Currency currency) {
        if (map.containsKey(amountValue)) {
            return map.get(amountValue);
        }
        return new AmountDTO(amountValue, currency.getCurrencyCode(), currency);
    }

    public static AmountDTO of(final long value) {
        final BigDecimal amountValue = BigDecimal.valueOf(value);
        if (map.containsKey(amountValue)) {
            return map.get(amountValue);
        }
        final AmountDTO amountDTO = new AmountDTO(amountValue);
        map.putIfAbsent(amountValue, amountDTO);
        return amountDTO;
    }

    public static AmountDTO of(BigDecimal amountValue, String unitOfMeasurement, Currency currency) {
        return new AmountDTO(amountValue, unitOfMeasurement, currency);
    }

    public AmountDTO minus(final AmountDTO otherAmount) {
        return AmountDTO.of(this.value.subtract(otherAmount.value), this.currency);
    }

    public AmountDTO pluse(AmountDTO otherAmount) {
        return AmountDTO.of(this.value.add(otherAmount.value), this.currency);
    }
}
