package com.fastturtle.redbusschemadesign.validators;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequestDTO;
import com.fastturtle.redbusschemadesign.models.CardDetails;

import java.util.Optional;

public interface PaymentValidator {
    Optional<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest);
}
