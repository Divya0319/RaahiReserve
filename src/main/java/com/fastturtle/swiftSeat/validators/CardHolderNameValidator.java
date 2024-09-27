package com.fastturtle.swiftSeat.validators;

import com.fastturtle.swiftSeat.dtos.PaymentRequestDTO;
import com.fastturtle.swiftSeat.models.CardDetails;

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
