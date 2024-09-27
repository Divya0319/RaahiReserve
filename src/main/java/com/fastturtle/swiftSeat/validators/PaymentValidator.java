package com.fastturtle.swiftSeat.validators;

import com.fastturtle.swiftSeat.dtos.PaymentRequestDTO;
import com.fastturtle.swiftSeat.models.CardDetails;

import java.util.Optional;

public interface PaymentValidator {
    Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest);
}
