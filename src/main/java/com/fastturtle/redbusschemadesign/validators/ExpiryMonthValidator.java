package com.fastturtle.redbusschemadesign.validators;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequestDTO;
import com.fastturtle.redbusschemadesign.models.CardDetails;

import java.util.Optional;

public class ExpiryMonthValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        if (!paymentRequest.getExpiryMonth().equals(cardDetails.getExpiryMonth())) {
            return Optional.of("Invalid expiry month.");
        }
        return Optional.empty();    }
}
