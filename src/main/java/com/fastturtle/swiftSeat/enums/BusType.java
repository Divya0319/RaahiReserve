package com.fastturtle.swiftSeat.enums;

public enum BusType {
    AC("AC"),
    NON_AC("Non-AC"),
    SLEEPER("Sleeper");

    private final String displayName;

    BusType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
