package com.fastturtle.raahiReserve.helpers.payment;

import com.fastturtle.raahiReserve.enums.PaymentMethod;
import com.fastturtle.raahiReserve.enums.PaymentRefType;
import com.fastturtle.raahiReserve.enums.PaymentStatus;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.BankAccountRepository;
import com.fastturtle.raahiReserve.repositories.BankDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetbankingPaymentStrategy implements PaymentStrategy {

    private final BankDetailRepository bankDetailRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public NetbankingPaymentStrategy(BankDetailRepository bankDetailRepository, BankAccountRepository bankAccountRepository) {
        this.bankDetailRepository = bankDetailRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Booking processPayment(Booking booking, PaymentStatus paymentStatus, PaymentParams paymentParams) {

        NetbankingPaymentParams netbankingPaymentParams = (NetbankingPaymentParams) paymentParams;
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.NETBANKING);
        payment.setPaymentStatus(paymentStatus);

        BankDetails bankDetails = bankDetailRepository.findByBankNameStartsWith(netbankingPaymentParams.getBankNamePrefix()).get(0);
        BankAccount bankAccount = bankAccountRepository.findBankAccountByBankDetailsAndUserID(bankDetails, netbankingPaymentParams.getUserId());

        payment.setPaymentReferenceId(bankAccount.getId());
        payment.setPaymentReferenceType(PaymentRefType.BANK);
        int receivedOtp = netbankingPaymentParams.getReceivedOtp();
        String otpString = String.valueOf(receivedOtp);
        if(otpString.length() == 6) {
            if(paymentStatus == PaymentStatus.COMPLETED) {
                bankAccount.setBalance(bankAccount.getBalance() - booking.getPrice());
                bankAccountRepository.save(bankAccount);
                System.out.println("OTP verified successfully: " + + booking.getPrice() + " " + booking.getTravelDate());
                payment.setPaymentStatus(PaymentStatus.COMPLETED);

            } else {
                System.out.println("Payment Failed: " + + booking.getPrice() + " " + booking.getTravelDate());
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
