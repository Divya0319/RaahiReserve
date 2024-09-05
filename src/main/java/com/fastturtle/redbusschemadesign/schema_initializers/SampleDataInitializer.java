package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.helpers.DateUtils;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.payment.PaymentParams;
import com.fastturtle.redbusschemadesign.payment.WalletPaymentParams;
import com.fastturtle.redbusschemadesign.payment.WalletPaymentStrategy;
import com.fastturtle.redbusschemadesign.repositories.*;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataInitializer {

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

    LocalTime[] busTiming = {
            LocalTime.parse("07:00:00"),
            LocalTime.parse("09:00:00"),
            LocalTime.parse("12:00:00"),
            LocalTime.parse("17:00:00"),
            LocalTime.parse("21:00:00"),
    };

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

    LocalDate[] bookingDates = {LocalDate.parse(DateUtils.convertDateFormat("02/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("03/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("10/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("12/12/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("08/08/2024"))
    };

    LocalDate[] paymentDates = {
            LocalDate.parse(DateUtils.convertDateFormat("08/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("08/10/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("15/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("12/12/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("15/08/2024"))
    };

    LocalDate[] travelDates = {
            LocalDate.parse(DateUtils.convertDateFormat("03/10/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("10/10/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("15/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("03/01/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("25/08/2024"))
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

    private final BusRouteRepository busRouteRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final BusSeatRepository busSeatRepository;
    private final BookingRepository bookingRepository;
    private final SeatCostRepository seatCostRepository;
    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserWalletRepository userWalletRepository;
    private final BankDetailRepository bankDetailRepository;
    private final CardDetailRepository cardDetailRepository;

    @Autowired
    public SampleDataInitializer(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusSeatRepository busSeatRepository, BookingRepository bookingRepository, SeatCostRepository seatCostRepository, PassengerRepository passengerRepository, BCryptPasswordEncoder passwordEncoder, UserWalletRepository userWalletRepository, BankDetailRepository bankDetailRepository, BankDetailRepository bankDetailRepository1, CardDetailRepository cardDetailRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busSeatRepository = busSeatRepository;
        this.bookingRepository = bookingRepository;
        this.seatCostRepository = seatCostRepository;
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userWalletRepository = userWalletRepository;
        this.bankDetailRepository = bankDetailRepository1;
        this.cardDetailRepository = cardDetailRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        createAndSaveBusesRoutesAndBusRoutes();
        createAndSaveUsers();
        creatingAndSavingSeatCosts();
        createAndInsert10MoreBuses();

        createAndSaveCardDetails();
        createAndSaveBankDetails();
        createAndSaveUserWallets();

        RandomSeatNumberProviderWithPreference rsnp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);
        Booking booking1 = createAndSaveBooking1(rsnp);
        Booking booking2 = createAndSaveBooking2(rsnp);
        Booking booking3 = createAndSaveBooking3(rsnp);
        Booking booking4 = createAndSaveBooking4(rsnp);
        Booking booking5 = createAndSaveBooking5(rsnp);

        markingTravelForBooking(booking1);
        markingTravelForBooking(booking2);
        markingTravelForBooking(booking3);
        markingTravelForBooking(booking4);
        markingTravelForBooking(booking5);
    }

    private void createAndSaveBusesRoutesAndBusRoutes() {
        for (int i = 0; i < busNos.length; i++) {
            Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                    busType[i], busTiming[i]);
            busRepository.save(bus);

            Route route = new Route(source[i], destination[i]);
            routeRepository.save(route);

            BusRoute busRoute = new BusRoute(bus, route, directions[i]);
            busRouteRepository.save(busRoute);
        }
    }

    private void createAndSaveUsers() {
        for(int i = 0; i < usernames.length; i++) {
            User user = new User(usernames[i], passwordEncoder.encode(passwords[i]), emails[i], userAges[i], userGenders[i], phNos[i]);
            userRepository.save(user);
        }
    }

    private void creatingAndSavingSeatCosts() {
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
    }

    private Booking createAndSaveBooking1(RandomSeatNumberProviderWithPreference rsnp) {
        BusRoute busRouteForBooking1 = busRouteRepository.findById(4).get();

        Booking booking1 = new Booking(userRepository.findById(4).get(), busRouteForBooking1, bookingDates[0], travelDates[0]);

        booking1.setUserPassenger(true);

        Bus busForBooking1 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking1).get(0);
        rsnp.setBusNo(busForBooking1.getBusNo());

        Float booking1Cost = 0.0f;

        if(booking1.isUserPassenger()) {
            BusSeat busSeatForUser = saveAssignedSeatToBusSeatEntityForBooking1(rsnp, busForBooking1, SeatType.AISLE);

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findCostByBusType(busTypeForUser);

            booking1Cost += seatCostForUser;

            createAndSaveUserPassengerToBooking1(booking1, busSeatForUser);

        }

        BusSeat busSeat1 = saveAssignedSeatToBusSeatEntityForBooking1(rsnp, busForBooking1, SeatType.WINDOW);

        BusSeat busSeat2 = saveAssignedSeatToBusSeatEntityForBooking1(rsnp, busForBooking1, SeatType.WINDOW);

        BusType busTypeForSeat1 = busSeatRepository.findBusTypeFromBusSeat(busSeat1);
        Float seatCostForSeat1 = seatCostRepository.findCostByBusType(busTypeForSeat1);

        BusType busTypeForSeat2 = busSeatRepository.findBusTypeFromBusSeat(busSeat2);
        Float seatCostForSeat2 = seatCostRepository.findCostByBusType(busTypeForSeat2);


        booking1.addPassenger(new Passenger(passengerNames[0], passengerAges[0], passengerGenders[0], busSeat1));
        booking1.addPassenger(new Passenger(passengerNames[1], passengerAges[1], passengerGenders[1], busSeat2));

        booking1Cost = booking1Cost + seatCostForSeat1 + seatCostForSeat2;
        booking1.setPrice(booking1Cost);

        Booking bookingWithPaymentAdded = createAndSavePendingPaymentForBooking(booking1);

        busForBooking1.setAvailableSeats(busForBooking1.getAvailableSeats() -
                bookingWithPaymentAdded.getPassengers().size());
        busRepository.save(busForBooking1);

        return bookingRepository.save(bookingWithPaymentAdded);

    }

    private void createAndSaveUserPassengerToBooking1(Booking booking1, BusSeat busSeatForUser) {
        Passenger userPassenger = new Passenger();
        userPassenger.setName(booking1.getUser().getUserName());
        userPassenger.setAge(booking1.getUser().getAge());
        userPassenger.setGender(booking1.getUser().getGender());
        userPassenger.setBusSeat(busSeatForUser);
        booking1.addPassenger(userPassenger);
    }

    private BusSeat saveAssignedSeatToBusSeatEntityForBooking1(RandomSeatNumberProviderWithPreference rsnp, Bus busForBooking1, SeatType seatPref) {
        int assignedSeat = rsnp.getRandomSeatNumberWithPreference(seatPref, true);
        BusSeat busSeat = new BusSeat();
        busSeat.setBus(busForBooking1);
        busSeat.setSeatNumber(assignedSeat);
        busSeat.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeat));
        busSeat.setOccupied(true);
        busSeat.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        return busSeatRepository.save(busSeat);
    }

    private Booking createAndSaveBooking2(RandomSeatNumberProviderWithPreference rsnp) {
        BusRoute busRouteForBooking2 = busRouteRepository.findById(3).get();

        Booking booking2 = new Booking(userRepository.findById(3).get(), busRouteForBooking2, bookingDates[1], travelDates[1]);

        Bus busForBooking2 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking2).get(0);
        rsnp.setBusNo(busForBooking2.getBusNo());

        int assignedSeatNo3 = rsnp.getRandomSeatNumberWithPreference(SeatType.AISLE, true);

        BusSeat busSeat3 = new BusSeat();
        busSeat3.setBus(busForBooking2);
        busSeat3.setSeatNumber(assignedSeatNo3);
        busSeat3.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo3));
        busSeat3.setOccupied(true);
        busSeat3.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

        busSeatRepository.save(busSeat3);

        BusType busTypeForSeat3 = busSeatRepository.findBusTypeFromBusSeat(busSeat3);
        Float seatCostForSeat3 = seatCostRepository.findCostByBusType(busTypeForSeat3);


        booking2.addPassenger(new Passenger(passengerNames[2], passengerAges[2], passengerGenders[2], busSeat3));
        booking2.setPrice(seatCostForSeat3);

        // Doing payment for Booking 2 via User wallet
        User user = userRepository.findByUserName("AliceSmith");
        WalletPaymentStrategy wps = new WalletPaymentStrategy(userWalletRepository);
        WalletPaymentParams walletPaymentParams = new WalletPaymentParams();
        walletPaymentParams.setUser(user);
        walletPaymentParams.setPaymentDate(paymentDates[3]);
        booking2 = wps.processPayment(booking2, PaymentStatus.COMPLETED, walletPaymentParams);

        return booking2;
    }

    private Booking createAndSaveBooking3(RandomSeatNumberProviderWithPreference rsnp) {
        BusRoute busRouteForBooking3 = busRouteRepository.findById(2).get();
        Booking booking3 = new Booking(userRepository.findById(2).get(), busRouteForBooking3, bookingDates[3], travelDates[3]);
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
        payment3.setPaymentMethod(PaymentMethod.DEBIT_CARD);

        // Doing payment via Debit Card
        CardDetails cardDetails = cardDetailRepository.findCardByEnding4DigitsAndType(1234, CardType.DEBIT).get(0);

        if(payment3.getPaymentMethod() == PaymentMethod.DEBIT_CARD) {
            payment3.setPaymentReferenceId(cardDetails.getCardId());
            payment3.setPaymentReferenceType(PaymentRefType.CARD);
            int receivedOtp = 840320;
            String otpString = String.valueOf(receivedOtp);
            if(otpString.length() == 6) {
                System.out.println("OTP verified successfully");
                payment3.setPaymentStatus(PaymentStatus.COMPLETED);
                payment3.setBooking(booking3);
                payment3.setAmount(booking3.getPrice());
                payment3.setPaymentDate(paymentDates[4]);
                booking3.setPayment(payment3);

                busForBooking3.setAvailableSeats(busForBooking3.getAvailableSeats() -
                        booking3.getPassengers().size());
                busRepository.save(busForBooking3);

                return bookingRepository.save(booking3);

            } else {
                System.out.println("Invalid OTP");
            }

        }

        return null;
    }

    private Booking createAndSaveBooking4(RandomSeatNumberProviderWithPreference rsnp) {
        BusRoute busRouteForBooking4 = busRouteRepository.findById(9).get();

        Booking booking4 = new Booking(userRepository.findById(5).get(), busRouteForBooking4, bookingDates[2], travelDates[2]);

        Bus busForBooking4 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking4).get(0);
        rsnp.setBusNo(busForBooking4.getBusNo());

        int assignedSeatNo5 = rsnp.getRandomSeatNumberWithPreference(SeatType.WINDOW, true);

        BusSeat busSeat5 = new BusSeat();
        busSeat5.setBus(busForBooking4);
        busSeat5.setSeatNumber(assignedSeatNo5);
        busSeat5.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo5));
        busSeat5.setOccupied(true);
        busSeat5.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

        busSeatRepository.save(busSeat5);

        BusType busTypeForSeat5 = busSeatRepository.findBusTypeFromBusSeat(busSeat5);
        Float seatCostForSeat5 = seatCostRepository.findCostByBusType(busTypeForSeat5);


        booking4.addPassenger(new Passenger(passengerNames[4], passengerAges[4], passengerGenders[4], busSeat5));

        booking4.setPrice(seatCostForSeat5);

        Payment payment4 = new Payment();
        payment4.setPaymentMethod(PaymentMethod.NETBANKING);

        // Doing Payment via NetBanking
        BankDetails bankDetails1 = bankDetailRepository.findByBankNameStartsWith("HDFC").get(0);

        if(payment4.getPaymentMethod() == PaymentMethod.NETBANKING) {
            payment4.setPaymentReferenceId(bankDetails1.getBankId());
            payment4.setPaymentReferenceType(PaymentRefType.BANK);
            int receivedOtp = 343532;
            String otpString = String.valueOf(receivedOtp);
            if(otpString.length() == 6) {
                System.out.println("OTP verified successfully");
                payment4.setPaymentStatus(PaymentStatus.COMPLETED);
                payment4.setBooking(booking4);
                payment4.setAmount(booking4.getPrice());
                payment4.setPaymentDate(paymentDates[2]);
                booking4.setPayment(payment4);

                busForBooking4.setAvailableSeats(busForBooking4.getAvailableSeats() -
                        booking4.getPassengers().size());
                busRepository.save(busForBooking4);

                return bookingRepository.save(booking4);

            } else {
                System.out.println("Invalid OTP");
            }

        }
        return null;
    }

    private Booking createAndSaveBooking5(RandomSeatNumberProviderWithPreference rsnp) {
        BusRoute busRouteForBooking5 = busRouteRepository.findById(14).get();

        Booking booking5 = new Booking(userRepository.findById(3).get(), busRouteForBooking5, bookingDates[4], travelDates[4]);

        Bus busForBooking5 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking5).get(0);
        rsnp.setBusNo(busForBooking5.getBusNo());

        int assignedSeatNo6 = rsnp.getRandomSeatNumber();

        BusSeat busSeat6 = new BusSeat();
        busSeat6.setBus(busForBooking5);
        busSeat6.setSeatNumber(assignedSeatNo6);
        busSeat6.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatNo6));
        busSeat6.setOccupied(true);
        busSeat6.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

        busSeatRepository.save(busSeat6);


        BusType busTypeForSeat6 = busSeatRepository.findBusTypeFromBusSeat(busSeat6);
        Float seatCostForSeat6 = seatCostRepository.findCostByBusType(busTypeForSeat6);

        booking5.addPassenger(new Passenger(passengerNames[5], passengerAges[5], passengerGenders[5], busSeat6));

        booking5.setPrice(seatCostForSeat6);

        Payment payment5 = new Payment();
        payment5.setPaymentMethod(PaymentMethod.NETBANKING);

        // Doing Payment via Netbanking
        BankDetails bankDetails2 = bankDetailRepository.findByBankNameStartsWith("Axis").get(0);

        if(payment5.getPaymentMethod() == PaymentMethod.NETBANKING) {
            payment5.setPaymentReferenceId(bankDetails2.getBankId());
            payment5.setPaymentReferenceType(PaymentRefType.BANK);
            int receivedOtp = 343532;
            String otpString = String.valueOf(receivedOtp);
            if(otpString.length() == 6) {
                System.out.println("OTP verified successfully");
                payment5.setPaymentStatus(PaymentStatus.COMPLETED);
                payment5.setBooking(booking5);
                payment5.setAmount(booking5.getPrice());
                payment5.setPaymentDate(paymentDates[4]);
                booking5.setPayment(payment5);

                // Saving booking1

                busForBooking5.setAvailableSeats(busForBooking5.getAvailableSeats() -
                        booking5.getPassengers().size());
                busRepository.save(busForBooking5);

                return bookingRepository.save(booking5);

            } else {
                System.out.println("Invalid OTP");
            }

        }
        return null;
    }

    private Booking createAndSavePendingPaymentForBooking(Booking booking) {
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setPaymentMethod(null);
        payment.setAmount(0.00f);
        payment.setPaymentDate(null);
        payment.setBooking(booking);
        booking.setPayment(payment);

        return booking;
    }

    private void markingTravelForBooking(Booking booking) {
        List<Passenger> listOfPassengersInB1 = bookingRepository.findAllPassengersInBooking(booking);
        List<Passenger> travellingPassengersB1 = new ArrayList<>();
        travellingPassengersB1.add(listOfPassengersInB1.get(0));

        for(Passenger p : listOfPassengersInB1) {
            p.setTraveled(travellingPassengersB1.contains(p));
        }

        // Updating all passenger's travel status belonging to a booking
        passengerRepository.saveAll(listOfPassengersInB1);

        List<Passenger> passengersInBooking = bookingRepository.findAllPassengersInBooking(booking);

        for(Passenger p : passengersInBooking) {
            BusSeat busSeat = p.getBusSeat();
            busSeat.setOccupied(false);
            busSeatRepository.save(busSeat);
        }

        Bus busForBooking = bookingRepository.findBusForBooking(booking.getBookingId());

        busForBooking.setAvailableSeats(busForBooking.getAvailableSeats() + passengersInBooking.size());
        busRepository.save(busForBooking);
    }

    private void createAndInsert10MoreBuses() {

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
        int[] availableSeats = {63, 60, 45, 48, 55, 45, 75, 30, 10, 40};

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
        Direction[] directions = {
                Direction.UP,
                Direction.DOWN,
                Direction.UP,
                Direction.UP,
                Direction.DOWN,
                Direction.UP,
                Direction.DOWN,
                Direction.UP,
                Direction.UP,
                Direction.DOWN
        };

        for (int i = 0; i < busNos.length; i++) {
            // Create and save Bus
            Bus bus = new Bus(busNos[i], busCompanyNames[i], totalSeats[i], availableSeats[i],
                    busType[i], busTiming[i]);
            busRepository.save(bus);

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

        for (int i = 0; i < 10; i++) {
            busRouteRepository.save(busRoutes[i]);
        }
    }

    private void createAndSaveCardDetails() {
        String[] cardNumbers = {"432111111234", "111123455432", "543211111234", "678943211234"};
        CardType[] cardTypes = {
                CardType.DEBIT,
                CardType.CREDIT,
                CardType.DEBIT,
                CardType.CREDIT
        };
        Byte[] expiryMonths = {11, 12, 8, 9};
        Integer[] expiryYears = {2027, 2029, 2026, 2025};
        String[] cVVs = {"123", "321", "456", "678"};

        for(int i = 0; i < cardNumbers.length; i++) {
            CardDetails cardDetails = new CardDetails(cardNumbers[i], cardTypes[i], expiryMonths[i], expiryYears[i], cVVs[i], true);
            cardDetailRepository.save(cardDetails);

        }
    }

    private void createAndSaveBankDetails() {
        String[] bankNames = {
                "HDFC Bank Ltd",
                "Axis Bank",
                "State Bank of India",
                "ICICI Bank",
                "Bank of Baroda",
                "Canara Bank",
                "Punjab National Bank",
                "Yes Bank Ltd",
                "Allahabad Bank",
                "Gramin Bank"
        };
        String[] bankCodes = {
                "HDFC1234567890123456",
                "AXIS1234567890123456",
                "SBI12345678901234567",
                "ICICI123456789012345",
                "BOB12345678901234567",
                "CANARA12345678901234",
                "PNB12345678901234567",
                "YES12345678901234567",
                "ALHBD123456789012345",
                "GRMN1234567890123456"
        };

        for(int i = 0; i < bankNames.length; i++) {
            BankDetails bankDetails = new BankDetails(bankNames[i], bankCodes[i]);
            bankDetailRepository.save(bankDetails);
        }
    }

    private void createAndSaveUserWallets() {
        List<User> users = userRepository.findAll();

        for(User user : users) {
            UserWallet uw = new UserWallet(BigDecimal.valueOf(2000));
            uw.setUser(user);
            userWalletRepository.save(uw);
        }
    }

}
