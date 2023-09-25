package com.generic.khatabook.entity;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

@Embeddable
public class Amount {

    public static final Currency DEFAULT = Currency.getInstance(Locale.getDefault());
    public static final Amount ZERO = new Amount();
    public static final Amount TEN = new Amount(BigDecimal.TEN);
    private final BigDecimal unitValue;
    private final String unitOfMeasurement;
    private final Currency currency;

    public Amount(BigDecimal unitValue, String unitOfMeasurement, Currency currency) {
        this.unitValue = unitValue;
        this.unitOfMeasurement = unitOfMeasurement;
        this.currency = currency;
    }

    public Amount() {
        this(BigDecimal.ZERO);

    }

    public Amount(final BigDecimal amount) {
        this(amount, DEFAULT.getCurrencyCode(), DEFAULT);
    }

    public static Amount of(BigDecimal amountValue, String unitOfMeasurement) {
        return new Amount(amountValue, unitOfMeasurement.toUpperCase(Locale.getDefault()), DEFAULT);
    }

    public static Amount of(BigDecimal amountValue, Currency currency) {
        return new Amount(amountValue, currency.getCurrencyCode(), currency);
    }

    public static Amount of(final long value) {
        return new Amount(BigDecimal.valueOf(value));
    }

    public BigDecimal unitValue() {
        return unitValue;
    }

    public String unitOfMeasurement() {
        return unitOfMeasurement;
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Amount) obj;
        return Objects.equals(this.unitValue, that.unitValue) && Objects.equals(this.unitOfMeasurement, that.unitOfMeasurement) && Objects.equals(this.currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitValue, unitOfMeasurement, currency);
    }

    @Override
    public String toString() {
        return "Amount[" + "unitValue=" + unitValue + ", " + "unitOfMeasurement=" + unitOfMeasurement + ", " + "currency=" + currency + ']';
    }


}
