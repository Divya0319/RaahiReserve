package com.fastturtle.raahiReserve.helpers.payment;

import java.time.LocalDateTime;

public abstract class PaymentParams {

    private LocalDateTime paymentDateTime;

    private int receivedOtp;

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public int getReceivedOtp() {
        return receivedOtp;
    }

    public void setReceivedOtp(int receivedOtp) {
        this.receivedOtp = receivedOtp;
    }
}
