package com.generic.khatabook.entity;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class GenerationDate {
    @CreationTimestamp
    private final LocalDateTime createdOn;
    @UpdateTimestamp
    private final LocalDateTime updatedOn;
    private final LocalDateTime deletedOn;

    public GenerationDate() {
        this(LocalDateTime.now(Clock.systemDefaultZone()));
    }

    public GenerationDate(LocalDateTime createdOn, LocalDateTime updatedOn,
                          LocalDateTime deletedOn) {
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.deletedOn = deletedOn;
    }

    public GenerationDate(final LocalDateTime createdOn) {
        this(createdOn, null, null);
    }

    public GenerationDate(final LocalDateTime createdOn, final LocalDateTime updateOn) {
        this(createdOn, updateOn, null);
    }

    public static GenerationDate creation() {
        return new GenerationDate(LocalDateTime.now(Clock.systemDefaultZone()));
    }

    public static GenerationDate modification() {
        return new GenerationDate(null, LocalDateTime.now(Clock.systemDefaultZone()));
    }

    public LocalDateTime createdOn() {
        return createdOn;
    }

    public LocalDateTime updatedOn() {
        return updatedOn;
    }

    public LocalDateTime deletedOn() {
        return deletedOn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GenerationDate) obj;
        return Objects.equals(this.createdOn, that.createdOn) &&
                Objects.equals(this.updatedOn, that.updatedOn) &&
                Objects.equals(this.deletedOn, that.deletedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, updatedOn, deletedOn);
    }

    @Override
    public String toString() {
        return "GenerationDate[" +
                "createdOn=" + createdOn + ", " +
                "updatedOn=" + updatedOn + ", " +
                "deletedOn=" + deletedOn + ']';
    }


}