package com.fastturtle.raahiReserve.helpers.payment;

import com.fastturtle.raahiReserve.enums.PaymentMethod;
import com.fastturtle.raahiReserve.enums.PaymentRefType;
import com.fastturtle.raahiReserve.enums.PaymentStatus;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.UserWalletRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
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
            String enteredEmail = "bob.johnson@yahoo.in";

            if(enteredEmail.equals(user.getEmail())) {
                int receivedOtp = walletParams.getReceivedOtp();
                String otpString = String.valueOf(receivedOtp);
                if(otpString.length() == 6) {
                    if(paymentStatus == PaymentStatus.COMPLETED) {
                        log.info("OTP verified successfully WALLET : {} {}",  booking.getPrice(), booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.COMPLETED);

                    } else {
                        log.info("Payment Failed WALLET: {} {}",booking.getPrice(), booking.getTravelDate());
                        payment.setPaymentStatus(PaymentStatus.FAILED);

                    }

                    walletBalance1 -= booking.getPrice();
                    userWallet.setBalance(BigDecimal.valueOf(walletBalance1));
                    userWalletRepository.save(userWallet);

                    payment.setBooking(booking);
                    payment.setAmount(booking.getPrice());
                    payment.setPaymentDate(paymentParams.getPaymentDate());
                    booking.setPayment(payment);

                    return booking;

                } else {
                    log.info("Invalid OTP");
                }
            }

        } else {
            log.info("Insufficient balance for payment of booking: {} {}", booking.getPrice(), booking.getTravelDate());
        }

        return null;
    }
}
