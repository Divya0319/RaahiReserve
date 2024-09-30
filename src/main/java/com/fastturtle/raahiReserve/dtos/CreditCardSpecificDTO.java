package com.fastturtle.raahiReserve.dtos;

import com.fastturtle.raahiReserve.models.BankDetails;

public class CreditCardSpecificDTO {

    private long totalCreditLimit;

    private long availableCreditLimit;

    private BankDetails bankDetails;

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

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }
}
