package com.fastturtle.raahiReserve.helpers.payment;

import com.fastturtle.raahiReserve.models.Booking;
import com.fastturtle.raahiReserve.enums.PaymentStatus;

public interface PaymentStrategy {

    Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams);
}
