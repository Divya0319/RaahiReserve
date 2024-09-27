package com.fastturtle.redbusschemadesign.helpers.payment;

import com.fastturtle.redbusschemadesign.enums.CardType;
import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.enums.PaymentRefType;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.BankAccountRepository;
import com.fastturtle.redbusschemadesign.repositories.CardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardPaymentStrategy implements PaymentStrategy {

    private final CardDetailRepository cardDetailRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public CardPaymentStrategy(CardDetailRepository cardDetailRepository, BankAccountRepository bankAccountRepository) {
        this.cardDetailRepository = cardDetailRepository;
        this.bankAccountRepository = bankAccountRepository;
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

        CardType cardType = cardParams.getCardType();

        List<CardDetails> cardDetails = cardDetailRepository.findCardByEnding4Digits(cardParams.getLast4Digits());
        List<CardDetails> filteredCards = cardDetails.stream().filter(
                cardType == CardType.DEBIT ?
                DebitCardDetails.class::isInstance
                : CreditCardDetails.class::isInstance)
                        .map(cardType == CardType.DEBIT ?
                                DebitCardDetails.class::cast
                                : CreditCardDetails.class::cast)
                                .toList();
        CardDetails filteredCard = filteredCards.get(0);


        payment.setPaymentReferenceId(filteredCard.getCardId());
        payment.setPaymentReferenceType(PaymentRefType.CARD);
        int receivedOtp = cardParams.getReceivedOtp();
        String otpString = String.valueOf(receivedOtp);
        if(otpString.length() == 6) {
            if(paymentStatus == PaymentStatus.COMPLETED) {
                if(cardParams.getCardType() == CardType.DEBIT && filteredCard instanceof DebitCardDetails debitCardDetails) {
                    BankAccount bankAccount = debitCardDetails.getBankAccount();
                    if(bankAccount.getBalance() >= booking.getPrice()) {
                        bankAccount.setBalance(bankAccount.getBalance() - booking.getPrice());
                        bankAccountRepository.save(bankAccount);
                        System.out.println("OTP verified successfully: " + booking.getPrice() + " " + booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    } else {
                        System.out.println("Insufficient balance in account");
                        System.out.println("Payment Failed: " + booking.getPrice() + " " + booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.FAILED);
                    }

                } else if(cardParams.getCardType() == CardType.CREDIT && filteredCard instanceof CreditCardDetails creditCardDetails) {
                    long availableCreditLimit = creditCardDetails.getAvailableCreditLimit();
                    if(availableCreditLimit >= booking.getPrice()) {
                        creditCardDetails.setAvailableCreditLimit(availableCreditLimit - (long)booking.getPrice());
                        cardDetailRepository.save(creditCardDetails);
                        System.out.println("OTP verified successfully: " + booking.getPrice() + " " + booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    } else {
                        System.out.println("Insufficient balance in card");
                        System.out.println("Payment Failed: " + booking.getPrice() + " " + booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.FAILED);
                    }
                }

            } else {
                System.out.println("Payment Failed: " + booking.getPrice() + " " + booking.getTravelDate());
                payment.setPaymentStatus(PaymentStatus.FAILED);

            }
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
