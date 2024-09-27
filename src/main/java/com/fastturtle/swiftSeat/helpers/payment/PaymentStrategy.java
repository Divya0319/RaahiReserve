package com.fastturtle.swiftSeat.helpers.payment;

import com.fastturtle.swiftSeat.models.Booking;
import com.fastturtle.swiftSeat.enums.PaymentStatus;

public interface PaymentStrategy {

    Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams);
}
