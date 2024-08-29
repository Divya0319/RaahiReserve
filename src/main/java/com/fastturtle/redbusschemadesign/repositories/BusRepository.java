package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.models.BusType;
import com.fastturtle.redbusschemadesign.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    @Query("SELECT b FROM Bus b " +
            "JOIN b.busRoutes br " +
            "JOIN br.route r " +
            "WHERE r.source = :source " +
            "AND r.destination = :destination " +
            "AND b.busType = :busType " +
            "AND b.availableSeats > 0"
    )
    List<Bus> findAvailableBusesBySourceAndDestinationAndBusType(@Param("source") String source, @Param("destination") String destination,
                                                                 @Param("busType") BusType busType);

    @Query("SELECT b.busType FROM Bus b")
    Set<BusType> getAllBusTypes();
}
