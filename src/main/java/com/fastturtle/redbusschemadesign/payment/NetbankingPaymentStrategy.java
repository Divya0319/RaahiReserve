package com.fastturtle.redbusschemadesign.payment;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentStatus;

public class NetbankingPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment processPayment(Booking booking, PaymentStatus paymentStatus) {
        return null;
    }
}
