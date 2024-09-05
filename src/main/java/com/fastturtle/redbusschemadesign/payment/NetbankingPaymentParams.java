package com.fastturtle.redbusschemadesign.payment;

public class NetbankingPaymentParams extends PaymentParams {
    private String bankNameSuffix;

    public String getBankNameSuffix() {
        return bankNameSuffix;
    }

    public void setBankNameSuffix(String bankNameSuffix) {
        this.bankNameSuffix = bankNameSuffix;
    }
}
