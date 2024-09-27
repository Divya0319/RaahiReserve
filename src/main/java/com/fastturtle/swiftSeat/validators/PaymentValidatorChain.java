package com.fastturtle.swiftSeat.validators;

import com.fastturtle.swiftSeat.dtos.PaymentRequestDTO;
import com.fastturtle.swiftSeat.models.CardDetails;

import java.util.ArrayList;
import java.util.List;

public class PaymentValidatorChain {

    private final List<PaymentValidator> validators = new ArrayList<>();

    public void addValidator(PaymentValidator validator) {
        validators.add(validator);
    }

    public List<String> validate(CardDetails cardDetails, PaymentRequestDTO paymentRequest) {
        List<String> errorMessages = new ArrayList<>();

        for(PaymentValidator validator : validators) {
            validator.validate(cardDetails, paymentRequest).ifPresent(errorMessages::add);
        }

        return errorMessages;
    }
}
