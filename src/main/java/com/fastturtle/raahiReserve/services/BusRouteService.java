package com.fastturtle.raahiReserve.services;


import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.enums.Direction;
import com.fastturtle.raahiReserve.models.Bus;
import com.fastturtle.raahiReserve.models.BusRoute;
import com.fastturtle.raahiReserve.models.Route;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusRouteRepository;
import com.fastturtle.raahiReserve.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

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

        Route route = new Route(source[i], destination[i]);
        routeRepository.save(route);

        BusRoute busRoute = new BusRoute(bus, route, directions[i]);
        busRouteRepository.save(busRoute);
    }
}
