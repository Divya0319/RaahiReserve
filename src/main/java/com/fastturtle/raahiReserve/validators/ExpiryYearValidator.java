package com.fastturtle.raahiReserve.validators;

import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.Optional;

public class ExpiryYearValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {

        if (!paymentRequest.getExpiryYear().equals(cardDetails.getExpiryYear())) {
            return Optional.of("Invalid expiry year.");
        }

        return Optional.empty();
    }
}
