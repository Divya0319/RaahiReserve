package com.fastturtle.raahiReserve.models;

import com.fastturtle.raahiReserve.enums.CardType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEBIT")
public class DebitCardDetails extends CardDetails {

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    public DebitCardDetails(String cardNumber, String cardHolderName, String cardCompany, Byte expiryMonth, Integer expiryYear, String cvv, Boolean isActive,
                            BankAccount bankAccount) {
        super(cardNumber, cardHolderName, cardCompany, expiryMonth, expiryYear, cvv, isActive);
        this.bankAccount = bankAccount;
    }

    public DebitCardDetails() {

    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public CardType getCardType() {
        return CardType.DEBIT;
    }
}
