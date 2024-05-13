package com.generic.khatabook.notebook.exceptions;

public enum AppEntity {

    CUSTOMER_SPECIFICATION("Customer Specification"), ID("Id"), PRODUCT("Product"), RATING("Rating"), KHATABOOK("Khatabook"),NOTEBOOK("Notebook"), SPECIFICATION("Specification"), CUSTOMER("Customer"), MSISDN("Mobile"), PAYMENT("Payment"), AMOUNT("Amount");
    private String myName;

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
}
