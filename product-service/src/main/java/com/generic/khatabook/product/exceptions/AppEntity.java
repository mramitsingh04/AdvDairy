package com.generic.khatabook.product.exceptions;

public enum AppEntity {

    ID("Id"),
    PRODUCT("Product"),
    RATING("Rating"),
    KHATABOOK("Khatabook"),
    SPECIFICATION("Specification"),
    CUSTOMER_SPECIFICATION("Customer Specification"),
    CUSTOMER("Customer"),
    MSISDN("Mobile");

    private final String myName;

    AppEntity(final String name) {
        myName = name;
    }

    public String getName() {
        return myName;
    }

    @Override
    public String toString() {
        return "Entity Name='" + myName + '\'';
    }

    public AppEntity or(final AppEntity customer) {
        return null;
    }
}
