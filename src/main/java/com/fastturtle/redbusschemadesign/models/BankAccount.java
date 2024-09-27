package com.fastturtle.redbusschemadesign.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bankAccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "accountNo", nullable = false, unique = true)
    private long accountNo;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "ifscCode")
    private String ifscCode;

    @Column(name = "branchCode")
    private String branchCode;

    @Column(name = "branchName")
    private String branchName;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankDetails bankDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BankAccount() {

    }

    public BankAccount(long accountNo, double balance, String ifscCode, String branchCode, String branchName) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.ifscCode = ifscCode;
        this.branchCode = branchCode;
        this.branchName = branchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
