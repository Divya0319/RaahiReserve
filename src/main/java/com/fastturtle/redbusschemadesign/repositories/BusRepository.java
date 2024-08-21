package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    @Query("SELECT b FROM Bus b " +
            "JOIN b.busRoutes br " +
            "WHERE br.route = :route " +
            "AND b.availableSeats > 0")
    List<Bus> findAvailableBusesOnRoute(@Param("route") Route route);

    @Query("SELECT b.totalSeats FROM Bus b WHERE b.busNo = :busNo")
    int findTotalSeatsByBusNo(@Param("busNo") String busNo);

    @Query("SELECT b.availableSeats FROM Bus b WHERE b.busNo = :busNo")
    int findAvailableSeatsByBusNo(@Param("busNo") String busNo);

    @Query("SELECT b FROM Bus b " +
            "JOIN b.busRoutes br " +
            "JOIN br.route r " +
            "WHERE r.source = :source " +
            "AND r.destination = :destination " +
            "AND b.availableSeats > 0")
    List<Bus> findAvailableBusesBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);
}
