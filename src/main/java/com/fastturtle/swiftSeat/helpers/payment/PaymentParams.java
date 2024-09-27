package com.fastturtle.swiftSeat.helpers.payment;

import java.time.LocalDate;

public abstract class PaymentParams {

    private LocalDate paymentDate;

    private int receivedOtp;

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getReceivedOtp() {
        return receivedOtp;
    }

    public void setReceivedOtp(int receivedOtp) {
        this.receivedOtp = receivedOtp;
    }
}
