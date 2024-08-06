package com.fastturtle.redbusschemadesign.models;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
