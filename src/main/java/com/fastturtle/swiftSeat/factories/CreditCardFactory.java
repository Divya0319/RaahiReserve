package com.fastturtle.swiftSeat.factories;

import com.fastturtle.swiftSeat.dtos.CardDTO;
import com.fastturtle.swiftSeat.helpers.CardUtils;
import com.fastturtle.swiftSeat.models.BankDetails;
import com.fastturtle.swiftSeat.models.CardDetails;
import com.fastturtle.swiftSeat.models.CreditCardDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CreditCardFactory implements CardFactory {

    @Override
    public CardDetails createCard(CardDTO dto, List<?> additionalData) {
        List<BankDetails> banks = (List<BankDetails>) additionalData;
        Random randomBank = new Random();
        int randomIndexBank = randomBank.nextInt(banks.size());

        CardUtils cardUtils = new CardUtils(dto.getCardCompany());
        long totalCreditLimit = cardUtils.getCreditLimit();
        long availableCreditLimit = (long) (cardUtils.getCreditLimit() - (0.1 * cardUtils.getCreditLimit()));

        return new CreditCardDetails(
                dto.getCardNumber(),
                dto.getCardHolderName(),
                dto.getCardCompany(),
                dto.getExpiryMonth(),
                dto.getExpiryYear(),
                dto.getCvv(),
                true,
                banks.get(randomIndexBank),
                totalCreditLimit,
                availableCreditLimit
        );
    }
}
