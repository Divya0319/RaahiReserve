package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.enums.BusType;
import com.fastturtle.redbusschemadesign.models.SeatCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatCostRepository extends JpaRepository<SeatCost, Integer> {

    @Query("SELECT sc.cost FROM SeatCost sc WHERE sc.busType = :busType")
    Float findCostByBusType(@Param("busType") BusType busType);
}
