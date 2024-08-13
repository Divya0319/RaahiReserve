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

@Component
public class SampleDataInitializer {
    private final BusRouteRepository busRouteRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final BusSeatRepository busSeatRepository;
    private final BookingRepository bookingRepository;
    private final SeatCostRepository seatCostRepository;

    @Autowired
    public SampleDataInitializer(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusSeatRepository busSeatRepository, BookingRepository bookingRepository, SeatCostRepository seatCostRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busSeatRepository = busSeatRepository;
        this.bookingRepository = bookingRepository;
        this.seatCostRepository = seatCostRepository;
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
        int[] userAges = {23, 46, 58, 33, 21};
        Gender[] userGenders = {Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.MALE};
        String[] phNos = {"9898976767", "7878765656", "8989877665", "9988656543", "8987967578"};

        LocalDate[] bookingDates = {LocalDate.parse(new DateFormatConverter().convertDateFormat("02/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("10/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("12/12/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/08/2024"))
        };

        LocalDate[] paymentDates = {
                LocalDate.parse(new DateFormatConverter().convertDateFormat("03/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/10/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("15/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("12/12/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/08/2024"))
        };
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

        // TODO : (DONE) assign bus seat to a passenger randomly from available seats, and if that seat is already booked, do another random search
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

            User user = new User(usernames[i], passwords[i], emails[i], userAges[i], userGenders[i], phNos[i]);
            userRepository.save(user);

        }

        SeatCost seatCost1 = new SeatCost();
        seatCost1.setBusType(BusType.NON_AC);
        seatCost1.setCost(200.0f);
        seatCostRepository.save(seatCost1);

        SeatCost seatCost2 = new SeatCost();
        seatCost2.setBusType(BusType.AC);
        seatCost2.setCost(300.0f);
        seatCostRepository.save(seatCost2);

        SeatCost seatCost3 = new SeatCost();
        seatCost3.setBusType(BusType.SLEEPER);
        seatCost3.setCost(500.0f);
        seatCostRepository.save(seatCost3);

        // Create and save new Booking
        BusRoute busRouteForBooking1 = busRouteRepository.findById(4).get();

        Booking booking1 = new Booking(userRepository.findById(4).get(), busRouteForBooking1, bookingDates[0]);

        booking1.setUserPassenger(true);

        RandomSeatNumberProvider rsnp = new RandomSeatNumberProvider(busRepository, busSeatRepository);

        Bus busForBooking1 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking1).get(0);
        rsnp.setBusNo(busForBooking1.getBusNo());

        Float booking1Cost = 0.0f;

        if(booking1.isUserPassenger()) {
            int assignedSeatForUser = rsnp.getRandomSeatNumber();
            BusSeat busSeatForUser = new BusSeat();
            busSeatForUser.setBus(busForBooking1);
            busSeatForUser.setSeatNumber(assignedSeatForUser);
            busSeatRepository.save(busSeatForUser);

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findCostByBusType(busTypeForUser);

            booking1Cost += seatCostForUser;

            Passenger userPassenger = new Passenger();
            userPassenger.setName(booking1.getUser().getUserName());
            userPassenger.setAge(booking1.getUser().getAge());
            userPassenger.setGender(booking1.getUser().getGender());
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

        BusType busTypeForSeat1 = busSeatRepository.findBusTypeFromBusSeat(busSeat1);
        Float seatCostForSeat1 = seatCostRepository.findCostByBusType(busTypeForSeat1);

        BusType busTypeForSeat2 = busSeatRepository.findBusTypeFromBusSeat(busSeat2);
        Float seatCostForSeat2 = seatCostRepository.findCostByBusType(busTypeForSeat2);


        booking1.addPassenger(new Passenger(passengerNames[0], passengerAges[0], passengerGenders[0], busSeat1));
        booking1.addPassenger(new Passenger(passengerNames[1], passengerAges[1], passengerGenders[1], busSeat2));

        booking1Cost = booking1Cost + seatCostForSeat1 + seatCostForSeat2;
        booking1.setPrice(booking1Cost);

        Payment payment1 = new Payment();
        payment1.setPaymentMethod(null);
        payment1.setPaymentStatus(paymentStatuses[4]);
        payment1.setBooking(booking1);
        payment1.setAmount(0.00f);
        payment1.setPaymentDate(null);
        booking1.setPayment(payment1);

        // Saving booking1
        bookingRepository.save(booking1);

        BusRoute busRouteForBooking2 = busRouteRepository.findById(3).get();

        Booking booking2 = new Booking(userRepository.findById(3).get(), busRouteForBooking2, bookingDates[1]);

        Bus busForBooking2 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking2).get(0);
        rsnp.setBusNo(busForBooking2.getBusNo());

        int assignedSeatNo3 = rsnp.getRandomSeatNumber();

        BusSeat busSeat3 = new BusSeat();
        busSeat3.setBus(busRepository.findById(2).get());
        busSeat3.setSeatNumber(assignedSeatNo3);

        busSeatRepository.save(busSeat3);

        BusType busTypeForSeat3 = busSeatRepository.findBusTypeFromBusSeat(busSeat3);
        Float seatCostForSeat3 = seatCostRepository.findCostByBusType(busTypeForSeat3);


        booking2.addPassenger(new Passenger(passengerNames[2], passengerAges[2], passengerGenders[2], busSeat3));
        booking2.setPrice(seatCostForSeat3);

        Payment payment2 = new Payment();
        payment2.setPaymentMethod(paymentMethods[3]);
        payment2.setPaymentStatus(paymentStatuses[3]);
        payment2.setBooking(booking2);
        payment2.setAmount(booking2.getPrice());
        payment2.setPaymentDate(paymentDates[3]);
        booking2.setPayment(payment2);

        // Saving booking2
        bookingRepository.save(booking2);

        BusRoute busRouteForBooking3 = busRouteRepository.findById(2).get();
        Booking booking3 = new Booking(userRepository.findById(2).get(), busRouteForBooking3, bookingDates[3]);
        booking3.setUserPassenger(true);

        Bus busForBooking3 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking3).get(0);
        rsnp.setBusNo(busForBooking3.getBusNo());

        Float booking3Cost = 0.0f;

        if(booking3.isUserPassenger()) {
            int assignedSeatForUser = rsnp.getRandomSeatNumber();
            BusSeat busSeatForUser = new BusSeat();
            busSeatForUser.setBus(busForBooking3);
            busSeatForUser.setSeatNumber(assignedSeatForUser);
            busSeatRepository.save(busSeatForUser);

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findCostByBusType(busTypeForUser);

            booking3Cost += seatCostForUser;

            Passenger userPassenger = new Passenger();
            userPassenger.setName(booking3.getUser().getUserName());
            userPassenger.setAge(booking3.getUser().getAge());
            userPassenger.setGender(booking3.getUser().getGender());
            userPassenger.setBusSeat(busSeatForUser);
            booking3.addPassenger(userPassenger);

        }

        int assignedSeatNo4 = rsnp.getRandomSeatNumber();

        BusSeat busSeat4 = new BusSeat();
        busSeat4.setBus(busForBooking3);
        busSeat4.setSeatNumber(assignedSeatNo4);

        busSeatRepository.save(busSeat4);

        BusType busTypeForSeat4 = busSeatRepository.findBusTypeFromBusSeat(busSeat4);
        Float seatCostForSeat4 = seatCostRepository.findCostByBusType(busTypeForSeat4);


        booking3.addPassenger(new Passenger(passengerNames[3], passengerAges[3], passengerGenders[3], busSeat4));

        booking3Cost = booking3Cost + seatCostForSeat4;
        booking3.setPrice(booking3Cost);

        Payment payment3 = new Payment();
        payment3.setPaymentMethod(paymentMethods[2]);
        payment3.setPaymentStatus(paymentStatuses[2]);
        payment3.setBooking(booking3);
        payment3.setAmount(booking3.getPrice());
        payment3.setPaymentDate(paymentDates[2]);
        booking3.setPayment(payment3);

        // Saving booking3
        bookingRepository.save(booking3);


    }


}
