package com.fastturtle.raahiReserve.validators;

import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.Optional;

public class CVVValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        if (!paymentRequest.getCvv().equals(cardDetails.getCvv())) {
            return Optional.of("Invalid CVV.");
        }

        return Optional.empty();
    }
}
