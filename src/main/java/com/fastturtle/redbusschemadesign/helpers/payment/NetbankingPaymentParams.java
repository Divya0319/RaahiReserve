package com.fastturtle.redbusschemadesign.helpers.payment;

public class NetbankingPaymentParams extends PaymentParams {
    private String bankNamePrefix;

    public String getBankNamePrefix() {
        return bankNamePrefix;
    }

    public void setBankNamePrefix(String bankNamePrefix) {
        this.bankNamePrefix = bankNamePrefix;
    }
}
