package com.generic.khatabook.product.model;

public enum UnitOfMeasurement {
    KILOGRAM("KG"), LITTER("L"), READING("R"), ITEM("I"), NONE("");
    private final String myUnitType;

    UnitOfMeasurement(final String unitType) {
        myUnitType = unitType;
    }

    public String getUnitType() {
        return myUnitType;
    }
}
