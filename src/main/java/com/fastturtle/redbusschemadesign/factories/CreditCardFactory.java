package com.fastturtle.redbusschemadesign.factories;

import com.fastturtle.redbusschemadesign.dtos.CardDTO;
import com.fastturtle.redbusschemadesign.helpers.CardUtils;
import com.fastturtle.redbusschemadesign.models.BankDetails;
import com.fastturtle.redbusschemadesign.models.CardDetails;
import com.fastturtle.redbusschemadesign.models.CreditCardDetails;
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
