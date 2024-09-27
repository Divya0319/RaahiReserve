package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BankAccount;
import com.fastturtle.redbusschemadesign.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    @Query("SELECT ba FROM BankAccount  ba " +
            "WHERE ba.bankDetails = :bankDetails " +
            "AND ba.user.userId = :userID")
    BankAccount findBankAccountByBankDetailsAndUserID(
            @Param("bankDetails") BankDetails bankDetails,
            @Param("userID") int userID);
}
