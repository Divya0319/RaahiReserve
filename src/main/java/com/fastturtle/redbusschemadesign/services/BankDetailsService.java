package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.BankDetails;
import com.fastturtle.redbusschemadesign.repositories.BankDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankDetailsService {

    private final BankDetailRepository bankDetailRepository;

    public BankDetailsService(BankDetailRepository bankDetailRepository) {
        this.bankDetailRepository = bankDetailRepository;
    }

    public List<BankDetails> getAllBankDetails() {
        return bankDetailRepository.findAll();
    }

    public List<BankDetails> getAllBankNames() {
        return bankDetailRepository.findAllBankNames();
    }

    public int finBankIDByBankName(String bankName) {
        return bankDetailRepository.findBankIDByBankName(bankName);
    }
}
