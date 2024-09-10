package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.enums.PaymentRefType;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public ResponseEntity<?> makePayment(PaymentRequest paymentRequest) {
        Optional<Booking> booking = bookingRepository.findById(paymentRequest.getBookingId());

        if(booking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } else {
            Payment payment = new Payment();
            payment.setBooking(booking.get());
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());

            return ResponseEntity.ok().body(paymentRepository.save(payment));
        }

    }

    public ResponseEntity<?> updatePayment(PaymentRequest paymentRequest) {
        Optional<Payment> payment = paymentRepository.findByBookingId(paymentRequest.getBookingId());
        if(payment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found for booking id " + paymentRequest.getBookingId());
        } else {
            Payment p = payment.get();
            p.setAmount(paymentRequest.getAmount());
            p.setPaymentMethod(paymentRequest.getPaymentMethod());
            p.setPaymentStatus(paymentRequest.getPaymentStatus());

            return ResponseEntity.ok().body(paymentRepository.save(p));

        }

    }

    public ResponseEntity<?> getPaymentStatus(int bookingId) {
        Optional<Payment> payment = paymentRepository.findByBookingId(bookingId);
        if(payment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found for booking id " + bookingId);
        } else {
            return ResponseEntity.ok().body(payment.get().getPaymentStatus());
        }

    }

    public void processPayment(int bookingId, PaymentRefType paymentRefType, String action) {
//        Optional<Payment> optionalPayment = paymentRepository.findByBookingId(bookingId);
//        Booking booking = bookingRepository.findByBookingId(bookingId).get();
//
//        Payment payment = optionalPayment.get();
//        payment.setPaymentMethod(paymentMode);
//        payment.setAmount(booking.getPrice());
//        payment.setPaymentDate(LocalDate.now());
//
//        if("completed".equals(action)) {
//            payment.setPaymentStatus(PaymentStatus.COMPLETED);
//        } else if("failed".equals(action)) {
//            payment.setPaymentStatus(PaymentStatus.FAILED);
//        }
//        paymentRepository.save(payment);

    }

    public ResponseEntity<?> checkPaymentStatusAndReturnBookingForBookingId(int bookingId) {
        ResponseEntity<?> response;
        Optional<Booking> booking = bookingRepository.findByBookingId(bookingId);

        if(booking.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found for booking id " + bookingId));
            return response;
        } else {
            Optional<Payment> optionalPayment = paymentRepository.findByBookingId(bookingId);

            Payment payment = optionalPayment.get();

            if(payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
                Float paidAmt = payment.getAmount();
                PaymentMethod chosenPaymentMode = payment.getPaymentMethod();
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of(
                                "warning", "Payment is Already Completed for booking id " + bookingId,
                                "paidAmt", paidAmt,
                                "chosenPaymentMode", chosenPaymentMode));
                return response;
            }

            response = ResponseEntity.ok(booking.get());
        }

        return response;

    }
}
