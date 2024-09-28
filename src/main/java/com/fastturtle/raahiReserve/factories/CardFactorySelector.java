package com.fastturtle.raahiReserve.factories;

import com.fastturtle.raahiReserve.dtos.CardDTO;
import com.fastturtle.raahiReserve.enums.CardType;
import com.fastturtle.raahiReserve.models.CardDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardFactorySelector {

    private final DebitCardFactory debitCardFactory;
    private final CreditCardFactory creditCardFactory;

    @Autowired
    public CardFactorySelector(DebitCardFactory debitCardFactory, CreditCardFactory creditCardFactory) {
        this.debitCardFactory = debitCardFactory;
        this.creditCardFactory = creditCardFactory;
    }

    public CardDetails createCard(CardDTO dto, CardType cardType, List<?> additionalData) {
        if(cardType == CardType.DEBIT) {
            return debitCardFactory.createCard(dto, additionalData);
        } else if(cardType == CardType.CREDIT) {
            return creditCardFactory.createCard(dto, additionalData);
        }

        throw new IllegalArgumentException("Unsupported card type: " + cardType);
    }
}
