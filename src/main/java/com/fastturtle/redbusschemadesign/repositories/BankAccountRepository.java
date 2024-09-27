package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BankAccount;
import com.fastturtle.redbusschemadesign.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    BankAccount findBankAccountByBankDetails(BankDetails bankDetails);
}
