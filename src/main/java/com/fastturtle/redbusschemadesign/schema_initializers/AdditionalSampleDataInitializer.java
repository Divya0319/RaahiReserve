package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRouteRepository;
import com.fastturtle.redbusschemadesign.repositories.RouteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

//@Component
public class AdditionalSampleDataInitializer {

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final BusRouteRepository busRouteRepository;

//    @Autowired
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
                "KA07WA7234",
                "CG14LT7402",
                "AP02LH2736",
                "PJ16TH1295",
                "MH04WM2756",
                "MH07HM7813",
                "CG04HJ7412",
                "AP11WA2746",
                "PJ02BL7215",
                "MH04PW2747"
        };
        int[] totalSeats = {65, 70, 45, 50, 55, 45, 80, 45, 60, 75};
        int[] availableSeats = {63, 60, 45, 48, 55, 45, 75, 30, 10,40};

        String[] busCompanyNames = {
                "Murgan Travels",
                "Soni Roadways",
                "Sania Travels",
                "Sukhi Tourism",
                "Gurudev Roadways",
                "Satnaam Travels",
                "Karan Roadways",
                "Sinha Tourism",
                "Lucky Transports",
                "Jolly Travels"

        };

        BusType[] busType = {
                BusType.AC,
                BusType.SLEEPER,
                BusType.NON_AC,
                BusType.NON_AC,
                BusType.AC,
                BusType.SLEEPER,
                BusType.SLEEPER,
                BusType.NON_AC,
                BusType.NON_AC,
                BusType.AC
        };

        LocalTime[] busTiming = {
                LocalTime.parse("07:00:00"),
                LocalTime.parse("09:00:00"),
                LocalTime.parse("12:00:00"),
                LocalTime.parse("17:00:00"),
                LocalTime.parse("12:00:00"),
                LocalTime.parse("07:00:00"),
                LocalTime.parse("09:00:00"),
                LocalTime.parse("17:00:00"),
                LocalTime.parse("07:00:00"),
                LocalTime.parse("12:00:00")
        };

        // Sample data for route

        String[] source = {"Chhattisgarh", "Mumbai", "Hyderabad", "Bangalore", "Goa", "Chhattisgarh", "Mumbai", "Hyderabad", "Bangalore", "Goa"};
        String[] destination = {"Mumbai", "Indore", "Chhattisgarh", "Punjab", "Mumbai", "Mumbai", "Indore", "Chhattisgarh", "Punjab", "Mumbai"};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.UP, Direction.UP, Direction.DOWN, Direction.UP, Direction.DOWN, Direction.UP, Direction.UP, Direction.DOWN};

        for (int i = 0; i < busNos.length; i++) {
            // Create and save Bus
            Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                    busType[i], busTiming[i]);
            busRepository.save(bus);

            // Create and save Route
//            Route route = new Route(source[i], destination[i]);
//            routeRepository.save(route);

        }

        List<Route> routes = routeRepository.findAll();
        List<Bus> buses = busRepository.findAll();

        BusRoute[] busRoutes = new BusRoute[10];

        // Create and save BusRoute
        busRoutes[0] = new BusRoute(buses.get(5), routes.get(3), directions[0]);
        busRoutes[1] = new BusRoute(buses.get(6), routes.get(2), directions[1]);
        busRoutes[2] = new BusRoute(buses.get(7), routes.get(4), directions[2]);
        busRoutes[3] = new BusRoute(buses.get(8), routes.get(1), directions[3]);
        busRoutes[4] = new BusRoute(buses.get(9), routes.get(0), directions[4]);
        busRoutes[5] = new BusRoute(buses.get(10), routes.get(3), directions[5]);
        busRoutes[6] = new BusRoute(buses.get(11), routes.get(2), directions[6]);
        busRoutes[7] = new BusRoute(buses.get(12), routes.get(4), directions[7]);
        busRoutes[8] = new BusRoute(buses.get(13), routes.get(3), directions[8]);
        busRoutes[9] = new BusRoute(buses.get(14), routes.get(2), directions[9]);

        for(int i = 0; i < 10; i++) {
            busRouteRepository.save(busRoutes[i]);
        }

    }
}
