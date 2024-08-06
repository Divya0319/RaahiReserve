package com.fastturtle.redbusschemadesign.dtos;

import com.fastturtle.redbusschemadesign.models.PaymentMethods;

public class PaymentRequest {
    private int bookingId;
    private float amount;
    private PaymentMethods paymentMethod;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
