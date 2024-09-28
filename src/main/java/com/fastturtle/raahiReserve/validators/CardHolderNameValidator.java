package com.fastturtle.raahiReserve.validators;

import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.Optional;

public class CardHolderNameValidator implements PaymentValidator {
    @Override
    public Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        if (!paymentRequest.getCardHolderName().equals(cardDetails.getCardHolderName())) {
            return Optional.of("Invalid cardholder name.");
        }

        return Optional.empty();
    }
}
