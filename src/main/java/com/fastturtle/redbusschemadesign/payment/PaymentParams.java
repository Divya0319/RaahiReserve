package com.fastturtle.redbusschemadesign.payment;

import java.time.LocalDate;

public abstract class PaymentParams {

    private LocalDate paymentDate;

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
