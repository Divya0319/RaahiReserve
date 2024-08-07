package com.fastturtle.redbusschemadesign.models;

public enum Direction {
    UP("UP"),
    DOWN("DOWN");

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
