package com.fastturtle.redbusschemadesign;

import com.fastturtle.redbusschemadesign.helpers.DateFormatConverter;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProvider;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.*;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class SampleDataInitializer {
    private final BusRouteRepository busRouteRepository;

    private final BusRepository busRepository;

    private final RouteRepository routeRepository;

    private final UserRepository userRepository;

    private final BusSeatRepository busSeatRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public SampleDataInitializer(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusSeatRepository busSeatRepository, BookingRepository bookingRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busSeatRepository = busSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        insertIntoEntitiesOneByOne();
    }



    private void insertIntoEntitiesOneByOne() {
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

        String[] source = {"Chattisgarh", "Mumbai", "Hyderabad", "Bangalore", "Goa"};
        String[] destination = {"Mumbai", "Indore", "Chhattisgarh", "Punjab", "Mumbai"};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.UP, Direction.UP, Direction.DOWN};

        String[] usernames = {"JohnDoe", "AliceSmith", "BobJohnson", "EmilyBrown", "MichaelDavis"};
        String[] passwords = {"doe@123", "smith@234", "john@345", "brown@456", "davis@567"};
        String[] emails = {"john.doe@example.com", "alice.smith@example.com", "bob.johnson@example.com", "emily.brown@example.com",
                "michael.davis@example.com"};
        String[] phNos = {"9898976767", "7878765656", "8989877665", "9988656543", "8987967578"};

        LocalDate[] bookingDates = {LocalDate.parse(new DateFormatConverter().convertDateFormat("02/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("10/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("12/12/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/08/2024"))
        };

        String[] paymentDates = {"03/09/2023", "08/10/2023", "15/09/2023", "12/12/2023", "08/08/2024"};
        PaymentMethods[] paymentMethods = {
                PaymentMethods.CASH,
                PaymentMethods.CREDIT_CARD,
                PaymentMethods.DEBIT_CARD,
                PaymentMethods.WALLET,
                PaymentMethods.DEBIT_CARD
        };
        PaymentStatus[] paymentStatuses = {
                PaymentStatus.COMPLETED,
                PaymentStatus.FAILED,
                PaymentStatus.COMPLETED,
                PaymentStatus.COMPLETED,
                PaymentStatus.PENDING
        };
        double[] amount = {300.00, 200.00, 400.00, 550.00, 450.00};


        // Sample data for Passenger
        String[] passengerNames = {
                "Alice Johnson",
                "Bob Smith",
                "Charlie Brown",
                "David Wilson",
                "Eva Davis",
                "Frank Moore",
                "Grace Taylor",
                "Hannah Anderson",
                "Isaac Thomas",
                "Julia Martin"
        };

        int[] passengerAges = {
                29,  // Alice Johnson
                34,  // Bob Smith
                28,  // Charlie Brown
                45,  // David Wilson
                31,  // Eva Davis
                22,  // Frank Moore
                39,  // Grace Taylor
                27,  // Hannah Anderson
                40,  // Isaac Thomas
                33   // Julia Martin
        };

        Gender[] passengerGenders = {
                Gender.FEMALE, // Alice Johnson
                Gender.MALE,   // Bob Smith
                Gender.OTHER,  // Charlie Brown
                Gender.MALE,   // David Wilson
                Gender.FEMALE, // Eva Davis
                Gender.MALE,   // Frank Moore
                Gender.OTHER,  // Grace Taylor
                Gender.FEMALE, // Hannah Anderson
                Gender.MALE,   // Isaac Thomas
                Gender.OTHER   // Julia Martin
        };

        // TODO : assign bus seat to a passenger randomly from available seats, and if that seat is already booked, do another random search
        // TODO: give option to choose aisle or window, depending on availability, allot them that seat

        // TODO: entry for bus seat booking will be done in bus_seat table, and it will hold only those entries of seats which are booked
        // TODO: once travel is done for a booking, vacate that seat from the bus(seems logical)

        // TODO: When a passenger confirms his travel, mark traveled = true for that booking id( need to rethink it)


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

            User user = new User(usernames[i], passwords[i], emails[i], phNos[i]);
            userRepository.save(user);

        }

        // Create and save new Booking
        Booking booking1 = new Booking(userRepository.findById(1).get(), busRouteRepository.findById(1).get(), bookingDates[0]);

        booking1.setUserPassenger(true);

        RandomSeatNumberProvider rsnp = new RandomSeatNumberProvider(busRepository, busSeatRepository);
        rsnp.setBusNo(busNos[0]);

        if(booking1.getUserPassenger()) {
            int assignedSeatForUser = rsnp.getRandomSeatNumber();
            BusSeat busSeatForUser = new BusSeat();
            busSeatForUser.setBus(busRepository.findById(1).get());
            busSeatForUser.setSeatNumber(assignedSeatForUser);
            busSeatRepository.save(busSeatForUser);

            Passenger userPassenger = new Passenger();
            userPassenger.setName(booking1.getUser().getUserName());
            userPassenger.setAge(45);
            userPassenger.setGender(passengerGenders[1]);
            userPassenger.setBusSeat(busSeatForUser);
            booking1.addPassenger(userPassenger);
        }

        int assignedSeatNo1 = rsnp.getRandomSeatNumber();

        BusSeat busSeat1 = new BusSeat();
        busSeat1.setBus(busRepository.findById(1).get());
        busSeat1.setSeatNumber(assignedSeatNo1);

        busSeatRepository.save(busSeat1);

        int assignedSeatNo2 = rsnp.getRandomSeatNumber();

        BusSeat busSeat2 = new BusSeat();
        busSeat2.setBus(busRepository.findById(1).get());
        busSeat2.setSeatNumber(assignedSeatNo2);

        busSeatRepository.save(busSeat2);


        booking1.addPassenger(new Passenger(passengerNames[0], passengerAges[0], passengerGenders[0], busSeat1));
        booking1.addPassenger(new Passenger(passengerNames[1], passengerAges[1], passengerGenders[1], busSeat2));

        bookingRepository.save(booking1);

        Booking booking2 = new Booking(userRepository.findById(2).get(), busRouteRepository.findById(2).get(), bookingDates[1]);

        rsnp.setBusNo(busNos[1]);

        int assignedSeatNo3 = rsnp.getRandomSeatNumber();

        BusSeat busSeat3 = new BusSeat();
        busSeat3.setBus(busRepository.findById(2).get());
        busSeat3.setSeatNumber(assignedSeatNo3);

        busSeatRepository.save(busSeat3);


        booking2.addPassenger(new Passenger(passengerNames[2], passengerAges[2], passengerGenders[2], busSeat3));

        bookingRepository.save(booking2);

    }


}
