package com.generic.khatabook.entity;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class TimePeriod {
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public TimePeriod() {
        this(LocalDate.MIN, LocalDate.MAX);
    }

    public TimePeriod(LocalDate from, LocalDate to) {
        this.fromDate = from;
        this.toDate = to;
    }

    public static TimePeriod of(final LocalDate from, final LocalDate to) {
        return new TimePeriod(from, to);
    }


    public boolean isValid(TimePeriod other) {

        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return (this.fromDate.isEqual(other.fromDate) || this.fromDate.isAfter(other.fromDate)) && this.toDate.isEqual(other.toDate) || this.toDate.isBefore(other.toDate);
    }

    public LocalDate from() {
        return fromDate;
    }

    public LocalDate to() {
        return toDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimePeriod) obj;
        return Objects.equals(this.fromDate, that.fromDate) && Objects.equals(this.toDate, that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate);
    }

    @Override
    public String toString() {
        return "TimePeriod[" + "from=" + fromDate + ", " + "to=" + toDate + ']';
    }


}
