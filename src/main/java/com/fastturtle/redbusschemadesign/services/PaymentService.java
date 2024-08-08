package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentStatus;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public Payment makePayment(PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        return paymentRepository.save(payment);
    }

    public Payment updatePayment(PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findByBookingId(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Payment not found for booking id : " + paymentRequest.getBookingId()));

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());

        return paymentRepository.save(payment);
    }

    public PaymentStatus getPaymentStatus(int bookingId) {

        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found for booking id : " + bookingId));

        return payment.getPaymentStatus();
    }
}
