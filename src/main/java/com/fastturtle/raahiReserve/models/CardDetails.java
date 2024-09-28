package com.fastturtle.raahiReserve.models;

import com.fastturtle.raahiReserve.enums.CardType;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cardDetails")
public abstract class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Column(name = "cardNumber", unique = true, nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "cardHolderName", nullable = false)
    private String cardHolderName;

    @Column(name = "cardCompany")
    private String cardCompany;

    @Column(name = "expiryMonth", nullable = false)
    private Byte expiryMonth;

    @Column(name = "expiryYear", nullable = false)
    private Integer expiryYear;

    @Column(name = "cvv", length = 3, nullable = false)
    private String cvv;

    @Column(name = "isActive")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "linked_user_id")
    private User linkedUser;


    public CardDetails(String cardNumber, String cardHolderName, String cardCompany, Byte expiryMonth, Integer expiryYear, String cvv, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardCompany = cardCompany;
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

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
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

    public User getLinkedUser() {
        return linkedUser;
    }

    public void setLinkedUser(User linkedUser) {
        this.linkedUser = linkedUser;
    }

    public abstract CardType getCardType();

}
