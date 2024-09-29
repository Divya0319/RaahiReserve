package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.enums.SeatType;
import com.fastturtle.raahiReserve.models.SeatCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatCostRepository extends JpaRepository<SeatCost, Integer> {

    @Query("SELECT sc.cost FROM SeatCost sc WHERE sc.busType = :busType AND sc.seatType = :seatType")
    Float findSeatCostByBusTypeAndSeatType(@Param("busType") BusType busType, @Param("seatType") SeatType seatType);
}
