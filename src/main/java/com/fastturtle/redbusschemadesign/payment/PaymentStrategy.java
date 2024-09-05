package com.fastturtle.redbusschemadesign.payment;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentStatus;

public interface PaymentStrategy {

    Payment processPayment(Booking booking, PaymentStatus paymentStatus);
}
