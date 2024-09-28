package com.fastturtle.raahiReserve.services;

import com.fastturtle.raahiReserve.models.BankAccount;
import com.fastturtle.raahiReserve.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;


    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> findBankAccountsForUser(Integer userId) {
        return bankAccountRepository.findBankAccountByUser_UserId(userId);
    }
}
