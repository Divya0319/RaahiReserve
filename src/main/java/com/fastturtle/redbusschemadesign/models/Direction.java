package com.fastturtle.redbusschemadesign.models;

public enum Direction {
    OUTBOUND("Outbound"),
    INBOUND("Inbound");

    private final String value;

    Direction(String value) {
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
