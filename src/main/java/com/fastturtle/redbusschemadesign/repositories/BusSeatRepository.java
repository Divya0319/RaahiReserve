package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BusSeat;
import com.fastturtle.redbusschemadesign.models.BusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusSeatRepository extends JpaRepository<BusSeat, Integer> {

    @Query("SELECT b.seatNumber FROM BusSeat b WHERE b.bus.busNo = :busNo")
    List<Integer> findAvailableSeatsByBusNo(String busNo);

    @Query("SELECT b.busType FROM BusSeat bs JOIN bs.bus b WHERE bs = :busSeat")
    BusType findBusTypeFromBusSeat(BusSeat busSeat);
}
