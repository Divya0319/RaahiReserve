package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.models.Bus;
import com.fastturtle.raahiReserve.models.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusRouteRepository extends JpaRepository<BusRoute, Integer> {

    @Query("SELECT br FROM BusRoute br JOIN br.route r WHERE r.source = :source AND r.destination = :destination")
    List<BusRoute> findBusRoutesBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);

    default BusRoute findFirstBusRouteBySourceAndDestination(String source, String destination) {
        return findBusRoutesBySourceAndDestination(source, destination).stream().findFirst().orElse(null);
    }

    @Query("SELECT br FROM BusRoute br " +
            " JOIN br.route r " +
            " JOIN br.bus b " +
            " WHERE r.source = :source " +
            "AND r.destination = :destination" +
            " AND b.busId = :busId"
    )
    List<BusRoute> findBusRoutesBySourceAndDestinationAndBus(@Param("source") String source, @Param("destination") String destination, @Param("busId") Integer busId);

    default BusRoute findFirstBusRouteBySourceAndDestinationAndBus(String source, String destination, Integer busId) {
        return findBusRoutesBySourceAndDestinationAndBus(source, destination, busId).stream().findFirst().orElse(null);
    }

    @Query("SELECT b FROM Bus b JOIN b.busRoutes br " +
            "WHERE br = :busRoute AND b.availableSeats > 0 " +
            "ORDER BY b.busId ASC"
    )
    List<Bus> findBusesAvailableInGivenBusRoute(@Param("busRoute") BusRoute busRoute);

    BusRoute findByBus_BusNo(String busNo);
}
