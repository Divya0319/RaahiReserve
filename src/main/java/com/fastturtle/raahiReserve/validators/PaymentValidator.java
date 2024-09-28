package com.fastturtle.raahiReserve.validators;

import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.Optional;

public interface PaymentValidator {
    Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest);
}
