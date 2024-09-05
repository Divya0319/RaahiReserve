package com.fastturtle.redbusschemadesign.payment;

import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.CardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements PaymentStrategy {

    private final CardDetailRepository cardDetailRepository;

    @Autowired
    public CardPaymentStrategy(CardDetailRepository cardDetailRepository) {
        this.cardDetailRepository = cardDetailRepository;
    }

    @Override
    public Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams) {
        CardPaymentParams cardParams = (CardPaymentParams) paymentParams;

        Payment payment = new Payment();
        if(cardParams.getCardType() == CardType.DEBIT) {
            payment.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        } else {
            payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        }
        payment.setPaymentStatus(paymentStatus);

        // Doing payment via Debit Card
        CardDetails cardDetails = cardDetailRepository.findCardByEnding4DigitsAndType(cardParams.getLast4Digits(), CardType.DEBIT).get(0);

        payment.setPaymentReferenceId(cardDetails.getCardId());
        payment.setPaymentReferenceType(PaymentRefType.CARD);
        int receivedOtp = 840320;
        String otpString = String.valueOf(receivedOtp);
        if(otpString.length() == 6) {
            System.out.println("OTP verified successfully");
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setBooking(booking);
            payment.setAmount(booking.getPrice());
            payment.setPaymentDate(paymentParams.getPaymentDate());
            booking.setPayment(payment);

            return booking;

        } else {
            System.out.println("Invalid OTP");
        }

        return null;
    }
}
