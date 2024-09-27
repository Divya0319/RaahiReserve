package com.fastturtle.swiftSeat.repositories;

import com.fastturtle.swiftSeat.enums.BusType;
import com.fastturtle.swiftSeat.enums.SeatType;
import com.fastturtle.swiftSeat.models.SeatCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatCostRepository extends JpaRepository<SeatCost, Integer> {

    @Query("SELECT sc.cost FROM SeatCost sc WHERE sc.busType = :busType AND sc.seatType = :seatType")
    Float findCostByBusTypeAnAndSeatType(@Param("busType") BusType busType, @Param("seatType") SeatType seatType);
}
