package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.enums.BusType;
import com.fastturtle.redbusschemadesign.models.Route;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusService {

    private final BusRepository busRepository;

    private final RouteRepository routeRepository;

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

    public List<Bus> findAvailableBusesBySourceAndDestination(String source, String destination) {
        return busRepository.findAvailableBusesBySourceAndDestination(source, destination);
    }

    public Map<BusType, List<Bus>> getAllBusesGroupedByTypeFilterBySourceAndDestination(String source, String destination) {
        List<Bus> buses = busRepository.findAvailableBusesBySourceAndDestination(source, destination);
        return buses.stream().collect(Collectors.groupingBy(Bus::getBusType));
    }
}
