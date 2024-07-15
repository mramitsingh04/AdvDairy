package com.generic.khatabook.room.service.entity;

import java.util.Objects;


public final class Customer {
    private final String customerId;
    private final String name;
    private final String details;

    public Customer(String customerId, String name, String details) {
        this.customerId = customerId;
        this.name = name;
        this.details = details;
    }

    public String customerId() {
        return customerId;
    }

    public String name() {
        return name;
    }

    public String details() {
        return details;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Customer) obj;
        return Objects.equals(this.customerId, that.customerId) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, details);
    }

    @Override
    public String toString() {
        return "Customer[" +
                "customerId=" + customerId + ", " +
                "name=" + name + ", " +
                "details=" + details + ']';
    }

}
