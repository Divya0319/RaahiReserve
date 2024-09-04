package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.CardDetails;
import com.fastturtle.redbusschemadesign.models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardDetailRepository extends JpaRepository<CardDetails, Integer> {

    @Query("SELECT cd FROM CardDetails cd WHERE cd.cardType = :cardType AND cd.cardNumber LIKE CONCAT('%', :ending4Digits)" )
    List<CardDetails> findCardByEnding4DigitsAndType(@Param("ending4Digits") Integer ending4Digits,
                                                     @Param("cardType") CardType cardType);
}
