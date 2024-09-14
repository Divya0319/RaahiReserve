package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.dtos.PaymentRequestDTO;
import com.fastturtle.redbusschemadesign.enums.CardType;
import com.fastturtle.redbusschemadesign.enums.PaymentRefType;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;
import com.fastturtle.redbusschemadesign.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;
    private final CardDetailRepository cardDetailRepository;
    private final UserWalletRepository userWalletRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, CardDetailRepository cardDetailRepository, UserWalletRepository userWalletRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.cardDetailRepository = cardDetailRepository;
        this.userWalletRepository = userWalletRepository;
        this.userRepository = userRepository;
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

    public void processPayment(PaymentRequestDTO paymentRequestDTO) {

        // Determine the payment type
        switch (paymentRequestDTO.getPaymentRefType()) {
            case CARD:
                // Process card payment
                processCardPayment(paymentRequestDTO);
                break;
            case BANK:
                // Process net banking payment
                processBankPayment(paymentRequestDTO);
                break;
            case USER:
                // Process wallet payment
                processWalletPayment(paymentRequestDTO);
                break;
        }

    }

    private void processCardPayment(PaymentRequestDTO dto) {
        Booking booking = bookingRepository.findByBookingId(dto.getBookingId()).orElse(null);
        Payment payment = booking.getPayment();
        if(dto.getPaymentMode() == PaymentMethod.DEBIT_CARD) {
            payment.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        } else {
            payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        }

        if(dto.getAction().equals("completed")) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        CardType cardType = dto.getPaymentMode() == PaymentMethod.DEBIT_CARD ? CardType.DEBIT : CardType.CREDIT;

        CardDetails cardDetails = cardDetailRepository.findByCardNumberAndCardType(dto.getCardNo(), cardType);
        if(cardDetails == null) {
            User user = userRepository.findById(dto.getUserID()).get();
            cardDetails = new CardDetails();
            cardDetails.setCardType(cardType);
            cardDetails.setCardNumber(dto.getCardNo());
            cardDetails.setCardCompany(dto.getCardCompany());
            cardDetails.setCardHolderName(dto.getCardHolderName());
            cardDetails.setCvv(dto.getCvv());
            cardDetails.setExpiryMonth(dto.getExpiryMonth());
            cardDetails.setExpiryYear(dto.getExpiryYear());
            cardDetails.setLinkedUser(user);
            cardDetailRepository.save(cardDetails);
            payment.setPaymentReferenceId(cardDetails.getCardId());
        } else {

            payment.setPaymentReferenceId(cardDetails.getCardId());
        }
        payment.setPaymentReferenceType(PaymentRefType.CARD);
        payment.setAmount(booking.getPrice());
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

    }

    private void processBankPayment(PaymentRequestDTO dto) {
        Booking booking = bookingRepository.findByBookingId(dto.getBookingId()).orElse(null);
        Payment payment = booking.getPayment();
        payment.setPaymentMethod(PaymentMethod.NETBANKING);

        if(dto.getAction().equals("completed")) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        payment.setPaymentReferenceId(dto.getBankID());
        payment.setPaymentReferenceType(PaymentRefType.BANK);
        payment.setAmount(booking.getPrice());
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

    }

    private void processWalletPayment(PaymentRequestDTO dto) {
        Booking booking = bookingRepository.findByBookingId(dto.getBookingId()).orElse(null);
        Payment payment = booking.getPayment();
        payment.setPaymentMethod(PaymentMethod.WALLET);
        if(dto.getAction().equals("completed")) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);

            UserWallet userWallet = userWalletRepository.findByUserId(dto.getUserID());
            userWallet.setBalance(userWallet.getBalance().subtract(BigDecimal.valueOf(booking.getPrice())));
            userWalletRepository.save(userWallet);

        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }
        payment.setPaymentReferenceId(dto.getUserID());
        payment.setPaymentReferenceType(PaymentRefType.USER);
        payment.setAmount(booking.getPrice());
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);
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
