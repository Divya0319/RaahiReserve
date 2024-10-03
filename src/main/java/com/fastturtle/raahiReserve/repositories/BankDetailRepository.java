package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankDetailRepository extends JpaRepository<BankDetails, Integer> {

    @Query("SELECT bd FROM BankDetails bd WHERE bd.bankName LIKE CONCAT(:phrase, '%')")
    List<BankDetails> findByBankNameStartsWith(@Param("phrase") String phrase);

    @Query("SELECT bd.bankName FROM BankDetails bd")
    List<BankDetails> findAllBankNames();

    @Query("SELECT b.bankId FROM BankDetails b WHERE b.bankName = :bankName")
    int findBankIDByBankName(@Param("bankName") String bankName);

    BankDetails findByBankCodeStartingWith(String bankCode);

}
