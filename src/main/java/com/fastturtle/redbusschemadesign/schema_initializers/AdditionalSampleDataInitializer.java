package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRouteRepository;
import com.fastturtle.redbusschemadesign.repositories.RouteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdditionalSampleDataInitializer {

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final BusRouteRepository busRouteRepository;

    @Autowired
    public AdditionalSampleDataInitializer(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busRouteRepository = busRouteRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        insertIntoBusEntity();
    }

    private void insertIntoBusEntity() {

        // Sample data for Bus
        String[] busNos = {
                "KA07HM7834",
                "CG04LM7492",
                "AP11HL2756",
                "PJ02HK7295",
                "MH04UK2743"
        };
        int[] totalSeats = {65, 70, 45, 50, 55};
        int[] availableSeats = {63, 60, 30, 20, 40};

        String[] busCompanyNames = {
                "Maya Travels",
                "Sunny Roadways",
                "Harleen Tourism",
                "Karan Travels",
                "Guru Roadways"

        };

        BusType[] busType = { BusType.AC,
                BusType.SLEEPER,
                BusType.NON_AC,
                BusType.NON_AC,
                BusType.SLEEPER};

        // Sample data for route

        String[] source = {"Chhattisgarh", "Mumbai", "Hyderabad", "Bangalore", "Goa"};
        String[] destination = {"Mumbai", "Indore", "Chhattisgarh", "Punjab", "Mumbai"};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.UP, Direction.UP, Direction.DOWN};

        for (int i = 0; i < busNos.length; i++) {
            // Create and save Bus
            Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                    busType[i]);
            busRepository.save(bus);

            // Create and save Route
            Route route = new Route(source[i], destination[i]);
            routeRepository.save(route);

            // Create and save BusRoute
            BusRoute busRoute = new BusRoute(bus, route, directions[i]
            );

            busRouteRepository.save(busRoute);

        }
    }
}
