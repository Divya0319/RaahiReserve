package com.fastturtle.swiftSeat.models;

import com.fastturtle.swiftSeat.enums.CardType;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CREDIT")
public class CreditCardDetails extends CardDetails {

    @Column(name = "totalCreditLimit")
    private long totalCreditLimit;

    @Column(name = "availableCreditLimit")
    private long availableCreditLimit;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankDetails bankDetails;

    public CreditCardDetails() {

    }

    public CreditCardDetails(String cardNumber, String cardHolderName, String cardCompany, Byte expiryMonth, Integer expiryYear, String cvv, Boolean isActive,
                             BankDetails bankDetails, long totalCreditLimit, long availableCreditLimit) {
        super(cardNumber, cardHolderName, cardCompany, expiryMonth, expiryYear, cvv, isActive);
        this.bankDetails = bankDetails;
        this.totalCreditLimit = totalCreditLimit;
        this.availableCreditLimit = availableCreditLimit;
    }

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

    @Override
    public CardType getCardType() {
        return CardType.CREDIT;
    }
}
