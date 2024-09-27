package com.fastturtle.redbusschemadesign.helpers.payment;

public class NetbankingPaymentParams extends PaymentParams {
    private String bankNamePrefix;

    private int userId;

    public String getBankNamePrefix() {
        return bankNamePrefix;
    }

    public void setBankNamePrefix(String bankNamePrefix) {
        this.bankNamePrefix = bankNamePrefix;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
