package com.fastturtle.redbusschemadesign.payment;

import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.UserRepository;
import com.fastturtle.redbusschemadesign.repositories.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletPaymentStrategy implements PaymentStrategy {

    private final UserWalletRepository userWalletRepository;

    @Autowired
    public WalletPaymentStrategy(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams) {
        WalletPaymentParams walletParams = (WalletPaymentParams) paymentParams;
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.WALLET);
        payment.setPaymentStatus(paymentStatus);

        User user = walletParams.getUser();
        UserWallet userWallet = userWalletRepository.findByUserId(user.getUserId());
        double walletBalance1 = userWallet.getBalance().doubleValue();

        if(booking.getPrice() <= walletBalance1) {
            String enteredEmail = "alice.smith@rediffmail.com";

            if(enteredEmail.equals(user.getEmail())) {
                int receivedOtp = 474649;
                String otpString = String.valueOf(receivedOtp);
                if(otpString.length() == 6) {
                    System.out.println("OTP verified successfully for booking 2");

                    walletBalance1 -= booking.getPrice();
                    userWallet.setBalance(BigDecimal.valueOf(walletBalance1));
                    userWalletRepository.save(userWallet);

                    payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    payment.setBooking(booking);
                    payment.setAmount(booking.getPrice());
                    payment.setPaymentDate(paymentParams.getPaymentDate());
                    booking.setPayment(payment);

                    return booking;

                } else {
                    System.out.println("Invalid OTP");
                }
            }

        } else {
            System.out.println("Insufficient balance for payment of booking 1");
        }

        return null;
    }
}
