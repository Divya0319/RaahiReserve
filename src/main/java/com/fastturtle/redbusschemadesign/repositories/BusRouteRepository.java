package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.models.BusRoute;
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

    @Query("SELECT b FROM Bus b JOIN b.busRoutes br " +
            "WHERE br = :busRoute AND b.availableSeats > 0 " +
            "ORDER BY b.busId ASC"
    )
    List<Bus> findBusesAvailableInGivenBusRoute(BusRoute busRoute);
}
