package com.fastturtle.swiftSeat.validators;

import com.fastturtle.swiftSeat.dtos.PaymentRequestDTO;
import com.fastturtle.swiftSeat.models.CardDetails;

import java.util.Optional;

public class ExpiryMonthValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        if (!paymentRequest.getExpiryMonth().equals(cardDetails.getExpiryMonth())) {
            return Optional.of("Invalid expiry month.");
        }
        return Optional.empty();    }
}
