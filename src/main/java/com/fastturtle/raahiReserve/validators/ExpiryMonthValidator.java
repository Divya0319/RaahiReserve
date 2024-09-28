package com.fastturtle.raahiReserve.validators;

import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.Optional;

public class ExpiryMonthValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        if (!paymentRequest.getExpiryMonth().equals(cardDetails.getExpiryMonth())) {
            return Optional.of("Invalid expiry month.");
        }
        return Optional.empty();    }
}
