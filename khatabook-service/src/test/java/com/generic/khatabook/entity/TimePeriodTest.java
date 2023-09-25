package com.generic.khatabook.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TimePeriodTest {
    @Test
    void testMinAndMaxLocalDate() {
        final TimePeriod timePeriod = new TimePeriod(LocalDate.MIN, LocalDate.MAX);
        final TimePeriod timePeriod1 = TimePeriod.of(LocalDate.MIN, LocalDate.MAX);
        assertThat(timePeriod.isValid(timePeriod1)).isTrue();
    }

    @Test
    void testEquals() {
        final TimePeriod timePeriod = new TimePeriod(LocalDate.MIN, LocalDate.MAX);
        final TimePeriod timePeriod1 = TimePeriod.of(LocalDate.MIN, LocalDate.MAX);
        assertThat(timePeriod.equals(timePeriod1)).isTrue();
        assertThat(timePeriod.hashCode() == timePeriod1.hashCode()).isTrue();
    }

    @Test
    void testValidForNull() {
        final TimePeriod timePeriod = new TimePeriod(LocalDate.MIN, LocalDate.MAX);
        final TimePeriod timePeriod1 = null;
        assertThat(timePeriod.isValid(timePeriod1)).isFalse();
    }

    @Test
    void testValidForSame() {
        final TimePeriod timePeriod = new TimePeriod(LocalDate.MIN, LocalDate.MAX);
        assertThat(timePeriod.isValid(timePeriod)).isTrue();
    }

    @Test
    void testToString() {
        final LocalDate now = LocalDate.now();
        final TimePeriod timePeriod = new TimePeriod(now, now.plusDays(4));
        String date = "TimePeriod[from=%s, to=%s]".formatted(now, now.plusDays(4));
        assertThat(timePeriod.toString()).isEqualTo(date);
    }
}