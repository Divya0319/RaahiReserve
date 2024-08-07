package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.models.Route;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    private BusRepository busRepository;

    private RouteRepository routeRepository;

    @Autowired
    public BusService(BusRepository busRepository, RouteRepository routeRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    public List<Bus> getAvailableBusesOnRoute(String source, String destination) {
        Route route = routeRepository.findBySourceAndDestination(source, destination);
        if(route == null) {
            throw new RuntimeException("No route found for source: " + source + " and destination: " + destination);
        }

        return busRepository.findAvailableBusesOnRoute(route);
    }
}
