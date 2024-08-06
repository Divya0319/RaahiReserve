package com.fastturtle.redbusschemadesign.models;

public enum PaymentMethods {
    CASH("Cash"),
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card"),
    WALLET("wallet");

    private final String value;

    PaymentMethods(String value) {
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
