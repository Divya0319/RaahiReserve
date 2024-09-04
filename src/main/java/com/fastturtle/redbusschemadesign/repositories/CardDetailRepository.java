package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDetailRepository extends JpaRepository<CardDetails, Integer> {
}
