package com.generic.khatabook.room.service.entity;

import java.util.List;
import java.util.Objects;

public final class Bad {
    private final int id;
    private final boolean isEmpty;
    private final Customer customer;
    private final List<Product> products;

    public Bad(int id, boolean isEmpty, Customer customer, List<Product> products) {
        this.id = id;
        this.isEmpty = isEmpty;
        this.customer = customer;
        this.products = products;
    }

    public int id() {
        return id;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Customer customer() {
        return customer;
    }

    public List<Product> products() {
        return products;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bad) obj;
        return this.id == that.id &&
                this.isEmpty == that.isEmpty &&
                Objects.equals(this.customer, that.customer) &&
                Objects.equals(this.products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isEmpty, customer, products);
    }

    @Override
    public String toString() {
        return "Bad[" +
                "id=" + id + ", " +
                "isEmpty=" + isEmpty + ", " +
                "customer=" + customer + ", " +
                "products=" + products + ']';
    }

}
