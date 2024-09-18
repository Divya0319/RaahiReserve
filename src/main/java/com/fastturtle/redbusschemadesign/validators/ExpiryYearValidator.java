package com.fastturtle.redbusschemadesign.validators;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequestDTO;
import com.fastturtle.redbusschemadesign.models.CardDetails;

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
