package com.fastturtle.raahiReserve.dtos;

import com.fastturtle.raahiReserve.enums.PaymentMethod;
import com.fastturtle.raahiReserve.enums.PaymentRefType;
import com.fastturtle.raahiReserve.models.BankAccount;

public class PaymentRequestDTO {
    private Integer bookingId;
    private PaymentRefType paymentRefType;
    private String action;
    private PaymentMethod paymentMode;

    // Card details
    private String cardNo;
    private String cardCompany;
    private Byte expiryMonth;
    private Integer expiryYear;
    private String cardHolderName;
    private String cvv;

    private DebitCardSpecificDTO debitCardSpecificDTO;
    private CreditCardSpecificDTO creditCardSpecificDTO;

    // Bank details
    private Integer bankID;
    private BankAccount bankAccount;
    private String maskedAccountNo;

    // Wallet details
    private Integer userID;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public PaymentRefType getPaymentRefType() {
        return paymentRefType;
    }

    public void setPaymentRefType(PaymentRefType paymentRefType) {
        this.paymentRefType = paymentRefType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PaymentMethod getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMethod paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
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

    public Integer getBankID() {
        return bankID;
    }

    public void setBankID(Integer bankID) {
        this.bankID = bankID;
    }

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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public DebitCardSpecificDTO getDebitCardSpecificDTO() {
        return debitCardSpecificDTO;
    }

    public void setDebitCardSpecificDTO(DebitCardSpecificDTO debitCardSpecificDTO) {
        this.debitCardSpecificDTO = debitCardSpecificDTO;
    }

    public CreditCardSpecificDTO getCreditCardSpecificDTO() {
        return creditCardSpecificDTO;
    }

    public void setCreditCardSpecificDTO(CreditCardSpecificDTO creditCardSpecificDTO) {
        this.creditCardSpecificDTO = creditCardSpecificDTO;
    }
}

