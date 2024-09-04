package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankDetailRepository extends JpaRepository<BankDetails, Integer> {

    @Query("SELECT bd FROM BankDetails bd WHERE bd.bankName LIKE :phrase%")
    List<BankDetails> findByBankNameStartsWith(@Param("phrase") String phrase);

}
