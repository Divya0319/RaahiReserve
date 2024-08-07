package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TravelRepository extends JpaRepository<Travel, Integer> {

    @Query("SELECT COUNT(p) FROM Travel t JOIN t.passengers p WHERE t.travelDate = :date")
    long countByTraveledDate(@Param("date") LocalDate date);
}
