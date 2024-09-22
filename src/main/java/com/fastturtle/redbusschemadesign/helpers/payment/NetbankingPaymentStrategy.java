package com.fastturtle.redbusschemadesign.helpers.payment;

import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.enums.PaymentRefType;
import com.fastturtle.redbusschemadesign.enums.PaymentStatus;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.BankDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetbankingPaymentStrategy implements PaymentStrategy {

    private final BankDetailRepository bankDetailRepository;

    @Autowired
    public NetbankingPaymentStrategy(BankDetailRepository bankDetailRepository) {
        this.bankDetailRepository = bankDetailRepository;
    }

    @Override
    public Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams) {

        NetbankingPaymentParams netbankingPaymentParams = (NetbankingPaymentParams) paymentParams;
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.NETBANKING);
        payment.setPaymentStatus(paymentStatus);

        BankDetails bankDetails = bankDetailRepository.findByBankNameStartsWith(netbankingPaymentParams.getBankNamePrefix()).get(0);

        payment.setPaymentReferenceId(bankDetails.getBankId());
        payment.setPaymentReferenceType(PaymentRefType.BANK);
        int receivedOtp = netbankingPaymentParams.getReceivedOtp();
        String otpString = String.valueOf(receivedOtp);
        if(otpString.length() == 6) {
            if(paymentStatus == PaymentStatus.COMPLETED) {
                System.out.println("OTP verified successfully");
                payment.setPaymentStatus(PaymentStatus.COMPLETED);

            } else {
                System.out.println("Payment Failed");
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
