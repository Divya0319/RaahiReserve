package com.fastturtle.swiftSeat.factories;

import com.fastturtle.swiftSeat.dtos.CardDTO;
import com.fastturtle.swiftSeat.models.BankAccount;
import com.fastturtle.swiftSeat.models.CardDetails;
import com.fastturtle.swiftSeat.models.DebitCardDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class DebitCardFactory implements CardFactory {

    @Override
    public CardDetails createCard(CardDTO dto, List<?> additionalData) {
        List<BankAccount> bankAccounts = (List<BankAccount>) additionalData;
        Random randomBankAcc = new Random();
        int randomIndexBankAcc = randomBankAcc.nextInt(bankAccounts.size());

        return new DebitCardDetails(
                dto.getCardNumber(),
                dto.getCardHolderName(),
                dto.getCardCompany(),
                dto.getExpiryMonth(),
                dto.getExpiryYear(),
                dto.getCvv(),
                true,
                bankAccounts.get(randomIndexBankAcc)
        );
    }
}
