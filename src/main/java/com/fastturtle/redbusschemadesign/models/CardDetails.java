package com.fastturtle.redbusschemadesign.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cardDetails")
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Column(name = "cardNumber", unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardType", nullable = false)
    private CardType cardType;

    @Column(name = "expiryMonth", nullable = false)
    private Byte expiryMonth;

    @Column(name = "expiryYear", nullable = false)
    private Integer expiryYear;

    @Column(name = "cvv", length = 3, nullable = false)
    private String cvv;

    @Column(name = "isActive")
    private Boolean isActive;

    public CardDetails(String cardNumber, CardType cardType, Byte expiryMonth, Integer expiryYear, String cvv, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.isActive = isActive;
    }

    public CardDetails() {

    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Byte getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Byte expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
