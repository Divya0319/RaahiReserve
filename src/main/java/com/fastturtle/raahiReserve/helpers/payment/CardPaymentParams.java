package com.fastturtle.raahiReserve.helpers.payment;

import com.fastturtle.raahiReserve.enums.CardType;

public class CardPaymentParams extends PaymentParams {

    private CardType cardType;
    private int last4Digits;

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getLast4Digits() {
        return last4Digits;
    }

    public void setLast4Digits(int last4Digits) {
        this.last4Digits = last4Digits;
    }
}
