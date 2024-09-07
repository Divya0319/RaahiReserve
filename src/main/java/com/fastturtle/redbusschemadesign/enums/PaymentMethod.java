package com.fastturtle.redbusschemadesign.enums;

public enum PaymentMethod {
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card"),
    NETBANKING("Net Banking"),
    WALLET("Wallet");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
