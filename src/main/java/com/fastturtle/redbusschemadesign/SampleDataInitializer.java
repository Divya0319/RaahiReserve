package com.fastturtle.redbusschemadesign;

import com.fastturtle.redbusschemadesign.helpers.DateFormatConverter;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.*;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

//@Component
public class SampleDataInitializer {
    private final BusRouteRepository busRouteRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final BusSeatRepository busSeatRepository;
    private final BookingRepository bookingRepository;
    private final SeatCostRepository seatCostRepository;
    private final TravelRepository travelRepository;
    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

//    @Autowired
    public SampleDataInitializer(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusSeatRepository busSeatRepository, BookingRepository bookingRepository, SeatCostRepository seatCostRepository, TravelRepository travelRepository, PassengerRepository passengerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busSeatRepository = busSeatRepository;
        this.bookingRepository = bookingRepository;
        this.seatCostRepository = seatCostRepository;
        this.travelRepository = travelRepository;
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
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

        String[] source = {"Chhattisgarh", "Mumbai", "Hyderabad", "Bangalore", "Goa"};
        String[] destination = {"Mumbai", "Indore", "Chhattisgarh", "Punjab", "Mumbai"};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.UP, Direction.UP, Direction.DOWN};

        String[] usernames = {"JohnDoe", "AliceSmith", "BobJohnson", "EmilyBrown", "MichaelDavis"};
        String[] passwords = {"doe@123", "smith@234", "john@345", "brown@456", "davis@567"};
        String[] emails = {"john.doe@gmail.com", "alice.smith@rediffmail.com", "bob.johnson@yahoo.in", "emily.brown@gmail.com",
                "michael.davis@rediffmail.com"};
        int[] userAges = {23, 46, 58, 33, 21};
        Gender[] userGenders = {Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.MALE};
        String[] phNos = {"9898976767", "7878765656", "8989877665", "9988656543", "8987967578"};

        LocalDate[] bookingDates = {LocalDate.parse(new DateFormatConverter().convertDateFormat("02/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("03/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("10/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("12/12/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/08/2024"))
        };

        LocalDate[] paymentDates = {
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("08/10/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("15/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("12/12/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("15/08/2024"))
        };

        LocalDate[] travelDates = {
                LocalDate.parse(new DateFormatConverter().convertDateFormat("03/10/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("10/10/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("15/09/2023")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("03/01/2024")),
                LocalDate.parse(new DateFormatConverter().convertDateFormat("25/08/2024"))
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

            User user = new User(usernames[i], passwordEncoder.encode(passwords[i]), emails[i], userAges[i], userGenders[i], phNos[i]);
            userRepository.save(user);

        }

        // Initialising seat costs for various bus types
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

        RandomSeatNumberProviderWithPreference rsnp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);

        Bus busForBooking1 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking1).get(0);
        rsnp.setBusNo(busForBooking1.getBusNo());

        Float booking1Cost = 0.0f;

        if(booking1.isUserPassenger()) {
            int assignedSeatForUser = rsnp.getRandomSeatNumberWithPreference(SeatType.AISLE, true);
            BusSeat busSeatForUser = new BusSeat();
            busSeatForUser.setBus(busForBooking1);
            busSeatForUser.setSeatNumber(assignedSeatForUser);
            busSeatForUser.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatForUser));
            busSeatForUser.setOccupied(true);
            busSeatForUser.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
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

        int assignedSeatNo1 = rsnp.getRandomSeatNumberWithPreference(SeatType.WINDOW, true);

        BusSeat busSeat1 = new BusSeat();
        busSeat1.setBus(busRepository.findById(1).get());
        busSeat1.setSeatNumber(assignedSeatNo1);
        busSeat1.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo1));
        busSeat1.setOccupied(true);
        busSeat1.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

        busSeatRepository.save(busSeat1);



        int assignedSeatNo2 = rsnp.getRandomSeatNumberWithPreference(SeatType.WINDOW, true);

        BusSeat busSeat2 = new BusSeat();
        busSeat2.setBus(busRepository.findById(1).get());
        busSeat2.setSeatNumber(assignedSeatNo2);
        busSeat2.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo2));
        busSeat2.setOccupied(true);
        busSeat2.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

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

        int assignedSeatNo3 = rsnp.getRandomSeatNumberWithPreference(SeatType.AISLE, true);

        BusSeat busSeat3 = new BusSeat();
        busSeat3.setBus(busRepository.findById(2).get());
        busSeat3.setSeatNumber(assignedSeatNo3);
        busSeat3.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo3));
        busSeat3.setOccupied(true);
        busSeat3.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

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
            int assignedSeatForUser = rsnp.getRandomSeatNumberWithPreference(SeatType.WINDOW, true);
            BusSeat busSeatForUser = new BusSeat();
            busSeatForUser.setBus(busForBooking3);
            busSeatForUser.setSeatNumber(assignedSeatForUser);
            busSeatForUser.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatForUser));
            busSeatForUser.setOccupied(true);
            busSeatForUser.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
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

        int assignedSeatNo4 = rsnp.getRandomSeatNumberWithPreference(SeatType.AISLE, true);

        BusSeat busSeat4 = new BusSeat();
        busSeat4.setBus(busForBooking3);
        busSeat4.setSeatNumber(assignedSeatNo4);
        busSeat4.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo4));
        busSeat4.setOccupied(true);
        busSeat4.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

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

        // Initiating travel from here
        Travel travel1 = new Travel();
        travel1.setTravelDate(travelDates[0]);
        travel1.setBooking(booking1);


        List<Passenger> listOfPassengersInB1 = bookingRepository.findAllPassengersInBooking(booking1);
        List<Passenger> travellingPassengersB1 = new ArrayList<>();
        travellingPassengersB1.add(listOfPassengersInB1.get(0));

        for(Passenger p : listOfPassengersInB1) {
            p.setTraveled(travellingPassengersB1.contains(p));
        }

        // Updating all passenger's travel status belonging to a booking
        passengerRepository.saveAll(listOfPassengersInB1);

        for(Passenger p : travellingPassengersB1) {
            travel1.addPassenger(p);
        }
        travelRepository.save(travel1);

        Booking bookingForTravel1 = travelRepository.findBookingForTravel(travel1);

        List<Passenger> passengersInBooking1 = bookingRepository.findAllPassengersInBooking(bookingForTravel1);

        for(Passenger p : passengersInBooking1) {
            BusSeat busSeat = p.getBusSeat();
            busSeat.setOccupied(false);
            busSeatRepository.save(busSeat);
        }

        Travel travel2 = new Travel();
        travel2.setTravelDate(travelDates[1]);
        travel2.setBooking(booking2);

        List<Passenger> listOfPassengersInB2 = bookingRepository.findAllPassengersInBooking(booking2);
        List<Passenger> travellingPassengersB2 = new ArrayList<>();
        travellingPassengersB2.add(listOfPassengersInB2.get(0));

        for(Passenger p : listOfPassengersInB2) {
            p.setTraveled(travellingPassengersB2.contains(p));
        }

        passengerRepository.saveAll(listOfPassengersInB2);

        for(Passenger p : travellingPassengersB2) {
            travel2.addPassenger(p);
        }

        travelRepository.save(travel2);

        // TODO: Bus seat vacating after travel is done
        Booking bookingForTravel2 = travelRepository.findBookingForTravel(travel2);

        List<Passenger> passengersInBooking2 = bookingRepository.findAllPassengersInBooking(bookingForTravel2);

        for(Passenger p : passengersInBooking2) {
            BusSeat busSeat = p.getBusSeat();
            busSeat.setOccupied(false);
            busSeatRepository.save(busSeat);
        }

        Travel travel3 = new Travel();
        travel3.setTravelDate(travelDates[3]);
        travel3.setBooking(booking3);

        List<Passenger> listOfPassengersInB3 = bookingRepository.findAllPassengersInBooking(booking3);
        List<Passenger> travellingPassengersB3 = new ArrayList<>();
        travellingPassengersB3.add(listOfPassengersInB3.get(0));

        for(Passenger p : listOfPassengersInB3) {
            p.setTraveled(travellingPassengersB3.contains(p));
        }

        passengerRepository.saveAll(listOfPassengersInB3);

        for (Passenger p : travellingPassengersB3) {
            travel3.addPassenger(p);

        }
        travelRepository.save(travel3);

        Booking bookingForTravel3 = travelRepository.findBookingForTravel(travel3);

        List<Passenger> passengersInBooking3 = bookingRepository.findAllPassengersInBooking(bookingForTravel3);
        for(Passenger p : passengersInBooking3) {
            BusSeat busSeat = p.getBusSeat();
            busSeat.setOccupied(false);
            busSeatRepository.save(busSeat);
        }

    }


}
