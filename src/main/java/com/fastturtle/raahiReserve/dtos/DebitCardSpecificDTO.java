package com.fastturtle.raahiReserve.dtos;

import com.fastturtle.raahiReserve.models.BankAccount;

public class DebitCardSpecificDTO {

    private BankAccount bankAccount;

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
