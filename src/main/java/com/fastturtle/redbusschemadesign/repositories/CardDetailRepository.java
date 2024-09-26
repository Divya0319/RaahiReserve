package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardDetailRepository extends JpaRepository<CardDetails, Integer> {

    @Query("SELECT cd FROM CardDetails cd WHERE cd.cardNumber LIKE CONCAT('%', :ending4Digits)" )
    List<CardDetails> findCardByEnding4Digits(@Param("ending4Digits") Integer ending4Digits);

    @Query("SELECT cd FROM CardDetails cd JOIN cd.linkedUser lu WHERE cd.linkedUser.userId = :userID")
    List<CardDetails> findCardsForUser(@Param("userID") Integer userID);

    CardDetails findByCardNumber(String cardNumber);
}
