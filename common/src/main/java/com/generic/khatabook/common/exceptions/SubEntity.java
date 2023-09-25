package com.generic.khatabook.common.exceptions;

public enum SubEntity {

    NAME("name"),
    UNIT_OF_MEASUREMENT("unitOfMeasurement");
    private String myName;

    SubEntity(final String name, SubEntity... subEntity) {
        myName = name;
    }

    public String getName() {
        return myName;
    }

}
