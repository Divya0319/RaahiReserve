package com.fastturtle.redbusschemadesign.models;

import com.fastturtle.redbusschemadesign.enums.CardType;
import jakarta.persistence.*;

@Entity
@Table(name = "cardDetails")
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Column(name = "cardNumber", unique = true, nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "cardHolderName", nullable = false)
    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardType", nullable = false)
    private CardType cardType;

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

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankDetails bankDetails;

    @Column(name = "totalCreditLimit")
    private long totalCreditLimit;

    @Column(name = "availableCreditLimit")
    private long availableCreditLimit;


    public CardDetails(String cardNumber, String cardHolderName, CardType cardType, String cardCompany, Byte expiryMonth, Integer expiryYear, String cvv, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
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

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
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

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
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
}
