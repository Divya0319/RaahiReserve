package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BusType;
import com.fastturtle.redbusschemadesign.models.SeatCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatCostRepository extends JpaRepository<SeatCost, Integer> {

    @Query("SELECT sc.cost FROM SeatCost sc WHERE sc.busType = :busType")
    Float findCostByBusType(BusType busType);
}
