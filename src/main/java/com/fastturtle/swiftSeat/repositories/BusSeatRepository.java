package com.fastturtle.swiftSeat.repositories;

import com.fastturtle.swiftSeat.models.BusSeat;
import com.fastturtle.swiftSeat.enums.BusType;
import com.fastturtle.swiftSeat.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusSeatRepository extends JpaRepository<BusSeat, Integer> {

    @Query("SELECT b.seatNumber FROM BusSeat b WHERE b.bus.busNo = :busNo AND b.occupied = true")
    List<Integer> findBookedSeatsByBusNo(@Param("busNo") String busNo);

    @Query("SELECT b.busType FROM BusSeat bs JOIN bs.bus b WHERE bs = :busSeat")
    BusType findBusTypeFromBusSeat(@Param("busSeat") BusSeat busSeat);

    @Query("SELECT b.seatType FROM BusSeat b WHERE b.seatNumber = :seatNo AND b.bus.busNo = :busNo")
    SeatType findSeatTypeFromSeatNumber(@Param("seatNo") int seatNo, @Param("busNo") String busNo);
}
