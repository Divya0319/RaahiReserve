package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.helpers.DateUtils;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.redbusschemadesign.models.*;
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

        Booking bookingWithPaymentAdded = createAndSavePaymentForBooking(booking1, PaymentStatus.PENDING, null, null, null);

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

        Payment payment2 = new Payment();
        payment2.setPaymentMethod(PaymentMethod.WALLET);


        // Doing payment via User wallet
        User user = userRepository.findByUserName("AliceSmith");
        UserWallet userWallet1 = userWalletRepository.findByUserId(user.getUserId());
        double walletBalance1 = userWallet1.getBalance().doubleValue();

        if(booking2.getPrice() <= walletBalance1) {
            String enteredEmail = "alice.smith@rediffmail.com";

            if(enteredEmail.equals(user.getEmail())) {
                int receivedOtp = 474649;
                String otpString = String.valueOf(receivedOtp);
                if(otpString.length() == 6) {
                    System.out.println("OTP verified successfully for booking 2");

                    walletBalance1 -= booking2.getPrice();
                    userWallet1.setBalance(BigDecimal.valueOf(walletBalance1));
                    userWalletRepository.save(userWallet1);

                    payment2.setPaymentStatus(PaymentStatus.COMPLETED);
                    payment2.setBooking(booking2);
                    payment2.setAmount(booking2.getPrice());
                    payment2.setPaymentDate(paymentDates[3]);
                    booking2.setPayment(payment2);

                    busForBooking2.setAvailableSeats(busForBooking2.getAvailableSeats() -
                            booking2.getPassengers().size());
                    busRepository.save(busForBooking2);

                    // Saving booking2
                    return bookingRepository.save(booking2);

                } else {
                    System.out.println("Invalid OTP");
                }
            }

        } else {
            System.out.println("Insufficient balance for payment of booking 1");
        }
        return null;
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

    private Booking createAndSavePaymentForBooking(Booking booking, PaymentStatus paymentStatus, PaymentMethod paymentMethod,
                                                   String bankNamePrefix, String cardSuffix) {
        Payment payment = new Payment();
        payment.setPaymentStatus(paymentStatus);

        if(paymentStatus == PaymentStatus.PENDING) {
            payment.setPaymentMethod(null);
            payment.setAmount(0.00f);
            payment.setPaymentDate(null);
            payment.setBooking(booking);
            booking.setPayment(payment);
        } else if(paymentMethod == PaymentMethod.NETBANKING){
            payment.setPaymentReferenceId(bankDetails.getBankId());
            payment.setPaymentReferenceType(PaymentRefType.BANK);
            int receivedOtp = 343532;
            String otpString = String.valueOf(receivedOtp);
            if(otpString.length() == 6) {
                System.out.println("OTP verified successfully");
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                payment.setBooking(booking);
                payment.setAmount(booking.getPrice());
                payment.setPaymentDate(paymentDates[4]);
                booking.setPayment(payment);

//                // Saving booking1
//
//                busForBooking5.setAvailableSeats(busForBooking5.getAvailableSeats() -
//                        booking5.getPassengers().size());
//                busRepository.save(busForBooking5);
//
//                return bookingRepository.save(booking5);

            } else {
                System.out.println("Invalid OTP");
            }
        }



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

}
