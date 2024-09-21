package com.fastturtle.redbusschemadesign.helpers.payment;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;

public interface PaymentStrategy {

    Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams);
}
