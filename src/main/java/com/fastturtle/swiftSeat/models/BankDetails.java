package com.fastturtle.swiftSeat.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bankDetails")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankId")
    private Integer bankId;

    @Column(name = "bankName", unique = true, nullable = false, length = 100)
    private String bankName;

    @Column(name = "bankCode", unique = true, nullable = false, length = 20)
    private String bankCode;

    public BankDetails(String bankName, String bankCode) {
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public BankDetails() {

    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
