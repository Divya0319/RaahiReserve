package com.fastturtle.raahiReserve.dtos;

import com.fastturtle.raahiReserve.models.BankAccount;

public class DebitCardSpecificDTO {

    private BankAccount bankAccount;

    private String maskedAccountNo;

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMaskedAccountNo() {
        return maskedAccountNo;
    }

    public void setMaskedAccountNo(String maskedAccountNo) {
        this.maskedAccountNo = maskedAccountNo;
    }
}
