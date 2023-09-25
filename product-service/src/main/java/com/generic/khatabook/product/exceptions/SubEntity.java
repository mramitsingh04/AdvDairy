package com.generic.khatabook.product.exceptions;

public enum SubEntity {

    NAME("name"),
    UNIT_OF_MEASUREMENT("unitOfMeasurement");
    private final String myName;

    SubEntity(final String name, SubEntity... subEntity) {
        myName = name;
    }

    public String getName() {
        return myName;
    }

}
