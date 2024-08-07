package com.fastturtle.redbusschemadesign.models;

public enum BusType {
    AC("AC"),
    NON_AC("NON_AC"),
    SLEEPER("SLEEPER");

    private final String value;

    BusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
