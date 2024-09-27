package com.fastturtle.swiftSeat.services;

import com.fastturtle.swiftSeat.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<String> findAllSources() {
        return routeRepository.findDistinctBySource();
    }

    public List<String> findAllDestinations() {
        return routeRepository.findDistinctByDestination();
    }
}
