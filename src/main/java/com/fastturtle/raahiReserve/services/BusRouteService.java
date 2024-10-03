package com.fastturtle.raahiReserve.services;


import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.enums.Direction;
import com.fastturtle.raahiReserve.models.Bus;
import com.fastturtle.raahiReserve.models.BusRoute;
import com.fastturtle.raahiReserve.models.Route;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusRouteRepository;
import com.fastturtle.raahiReserve.repositories.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Log4j2
@Service
public class BusRouteService {


    @Transactional
    public void createAndSaveSingleBusAndRoute(int i, String[] busNos, String[] busCompanyNames, int[] totalSeats,
                                               int[] availableSeats, BusType[] busType, LocalTime[] busTiming,
                                               String[] source, String[] destination, Direction[] directions,
                                               BusRepository busRepository, RouteRepository routeRepository,
                                               BusRouteRepository busRouteRepository) {

        Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                busType[i], busTiming[i]);
        busRepository.save(bus);

        log.info("Bus {} has been created", bus.getBusNo());

        Route route = new Route(source[i], destination[i]);
        routeRepository.save(route);

        log.info("Route from {} to {} has been created", route.getSource(), route.getDestination());

        BusRoute busRoute = new BusRoute(bus, route, directions[i]);
        busRouteRepository.save(busRoute);

        log.info("Bus Route for {} has been created", busRoute.getBus().getBusNo());
    }

    @Transactional
    public void createAndInsert10MoreBuses() {
            // Create and save Bus
        Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                busType[i], busTiming[i]);
        busRepository.save(bus);


    }
}
