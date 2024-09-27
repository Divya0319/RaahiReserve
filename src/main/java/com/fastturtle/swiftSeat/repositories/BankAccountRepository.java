package com.fastturtle.swiftSeat.repositories;

import com.fastturtle.swiftSeat.models.BankAccount;
import com.fastturtle.swiftSeat.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    @Query("SELECT ba FROM BankAccount  ba " +
            "WHERE ba.bankDetails = :bankDetails " +
            "AND ba.user.userId = :userID")
    BankAccount findBankAccountByBankDetailsAndUserID(
            @Param("bankDetails") BankDetails bankDetails,
            @Param("userID") int userID);

    List<BankAccount> findBankAccountByBankDetails_BankIdAndUser_UserId(Integer bankId, Integer userId);
}
