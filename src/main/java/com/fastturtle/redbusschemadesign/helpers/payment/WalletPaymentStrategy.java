package com.fastturtle.redbusschemadesign.helpers.payment;

import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.enums.PaymentRefType;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;
import com.fastturtle.redbusschemadesign.models.*;
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

        payment.setPaymentReferenceId(userWallet.getWalletId());
        payment.setUser(user);
        payment.setPaymentReferenceType(PaymentRefType.USER);

        if(booking.getPrice() <= walletBalance1) {
            String enteredEmail = "alice.smith@rediffmail.com";

            if(enteredEmail.equals(user.getEmail())) {
                int receivedOtp = walletParams.getReceivedOtp();
                String otpString = String.valueOf(receivedOtp);
                if(otpString.length() == 6) {
                    System.out.println("OTP verified successfully");

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
