package com.generic.khatabook.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

class AmountTest {
    @Test
    void test() throws IOException {
        final Currency currency = Currency.getInstance(Locale.getDefault());
        Assertions.assertThat(Amount.of(100)).isEqualTo(new Amount(BigDecimal.valueOf(100), currency.getCurrencyCode(), currency));
        final Currency currency1 = Currency.getInstance(new Locale("en", "in"));

        Assertions.assertThat(Amount.of(100)).isNotEqualTo(new Amount(BigDecimal.valueOf(100), currency1.getCurrencyCode(), currency1));
    }

}