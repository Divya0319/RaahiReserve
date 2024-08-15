package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.Route;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusService {

    private BusRepository busRepository;

    private RouteRepository routeRepository;

    @Autowired
    public BusService(BusRepository busRepository, RouteRepository routeRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    public ResponseEntity<?> getAvailableBusesOnRoute(String source, String destination) {
        Route route = routeRepository.findBySourceAndDestination(source, destination);
        ResponseEntity<?> response;
        if(route == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",
                    "No route found for source: " + source + " and destination: " + destination));
        } else {
            response = ResponseEntity.ok(busRepository.findAvailableBusesOnRoute(route));
        }

        return response;
    }
}
