package com.fastturtle.redbusschemadesign.factories;

import com.fastturtle.redbusschemadesign.dtos.CardDTO;
import com.fastturtle.redbusschemadesign.models.BankAccount;
import com.fastturtle.redbusschemadesign.models.CardDetails;
import com.fastturtle.redbusschemadesign.models.DebitCardDetails;
import com.fastturtle.redbusschemadesign.repositories.BankAccountRepository;
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
