package com.fastturtle.raahiReserve.dtos;

public class CreditCardSpecificDTO {

    private long totalCreditLimit;
    private long availableCreditLimit;

    public long getTotalCreditLimit() {
        return totalCreditLimit;
    }

    public void setTotalCreditLimit(long totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }

    public long getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(long availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }
}
