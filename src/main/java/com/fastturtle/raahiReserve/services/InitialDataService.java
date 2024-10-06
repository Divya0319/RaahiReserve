package com.fastturtle.raahiReserve.services;

import com.fastturtle.raahiReserve.dtos.CardDTO;
import com.fastturtle.raahiReserve.enums.*;
import com.fastturtle.raahiReserve.factories.CardFactorySelector;
import com.fastturtle.raahiReserve.helpers.DateUtils;
import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.helpers.payment.*;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class InitialDataService {

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
    String[] fullNames = {"John Doe", "Alice Smith", "Bob Johnson", "Emily Brown", "Michael Davis"};
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
            LocalDate.parse(DateUtils.convertDateFormat("08/08/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("01/09/2024"))
    };

    LocalDate[] paymentDates = {
            LocalDate.parse(DateUtils.convertDateFormat("08/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("08/10/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("15/09/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("12/12/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("15/08/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("01/09/2024"))
    };

    LocalDate[] travelDates = {
            LocalDate.parse(DateUtils.convertDateFormat("15/09/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("10/10/2023")),
            LocalDate.parse(DateUtils.convertDateFormat("23/09/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("03/01/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("25/08/2024")),
            LocalDate.parse(DateUtils.convertDateFormat("24/09/2024"))
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
    private final BankAccountRepository bankAccountRepository;
    private final CardFactorySelector cardFactorySelector;
    private final PaymentService paymentService;

    @Autowired
    public InitialDataService(BusRepository busRepository, RouteRepository routeRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusSeatRepository busSeatRepository, BookingRepository bookingRepository, SeatCostRepository seatCostRepository, PassengerRepository passengerRepository, BCryptPasswordEncoder passwordEncoder, UserWalletRepository userWalletRepository, BankDetailRepository bankDetailRepository, CardDetailRepository cardDetailRepository, BankAccountRepository bankAccountRepository, CardFactorySelector cardFactorySelector, PaymentService paymentService) {
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
        this.bankDetailRepository = bankDetailRepository;
        this.cardDetailRepository = cardDetailRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.cardFactorySelector = cardFactorySelector;
        this.paymentService = paymentService;

    }

    @Transactional
    public void createAndSaveBusesAndBusRoutes() {
        ExecutorService executorService = Executors.newFixedThreadPool(busNos.length);
        for (int i = 0; i < busNos.length; i++) {
            final int index = i;

            executorService.submit(() -> {
                Bus bus = new Bus(busNos[index], busCompanyNames[index], totalSeats[index], availableSeats[index],
                        busType[index], busTiming[index]);
                busRepository.save(bus);

//                log.info("Bus {} has been created", bus.getBusNo());

                Route route = new Route(source[index], destination[index]);
                routeRepository.save(route);

//                log.info("Route from {} to {} has been created", route.getSource(), route.getDestination());

                BusRoute busRoute = new BusRoute(bus, route, directions[index]);
                busRouteRepository.save(busRoute);

//                log.info("Bus Route for {} has been created", busRoute.getBus().getBusNo());
            });


        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    @Transactional
    public void createAndInsert10MoreBuses() {

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

        ExecutorService executorService = Executors.newFixedThreadPool(busNos.length);

        // Create and save Bus in parallel
        for (int i = 0; i < busNos.length; i++) {
            final int index = i;

            executorService.submit(() -> {
                Bus bus = new Bus(busNos[index], busCompanyNames[index], totalSeats[index], availableSeats[index],
                        busType[index], busTiming[index]);
                busRepository.save(bus);

//                log.info("Another Bus {} has been created", bus.getBusNo());
            });

        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ExecutorService routeExecutorService = Executors.newFixedThreadPool(busNos.length);

        BusRoute[] busRoutes = new BusRoute[10];

        // Create and save BusRoute
        busRoutes[0] = new BusRoute(busRepository.findByBusNo("KA07WA7234"), routeRepository.findBySourceAndDestination("Bangalore", "Punjab"), directions[0]);
        busRoutes[1] = new BusRoute(busRepository.findByBusNo("CG14LT7402"), routeRepository.findBySourceAndDestination("Hyderabad", "Chhattisgarh"), directions[1]);
        busRoutes[2] = new BusRoute(busRepository.findByBusNo("AP02LH2736"), routeRepository.findBySourceAndDestination("Goa", "Mumbai"), directions[2]);
        busRoutes[3] = new BusRoute(busRepository.findByBusNo("PJ16TH1295"), routeRepository.findBySourceAndDestination("Mumbai", "Indore"), directions[3]);
        busRoutes[4] = new BusRoute(busRepository.findByBusNo("MH04WM2756"), routeRepository.findBySourceAndDestination("Chhattisgarh", "Mumbai"), directions[4]);
        busRoutes[5] = new BusRoute(busRepository.findByBusNo("MH07HM7813"), routeRepository.findBySourceAndDestination("Bangalore", "Punjab"), directions[5]);
        busRoutes[6] = new BusRoute(busRepository.findByBusNo("CG04HJ7412"), routeRepository.findBySourceAndDestination("Hyderabad", "Chhattisgarh"), directions[6]);
        busRoutes[7] = new BusRoute(busRepository.findByBusNo("AP11WA2746"), routeRepository.findBySourceAndDestination("Goa", "Mumbai"), directions[7]);
        busRoutes[8] = new BusRoute(busRepository.findByBusNo("PJ02BL7215"), routeRepository.findBySourceAndDestination("Bangalore", "Punjab"), directions[8]);
        busRoutes[9] = new BusRoute(busRepository.findByBusNo("MH04PW2747"), routeRepository.findBySourceAndDestination("Hyderabad", "Chhattisgarh"), directions[9]);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            routeExecutorService.submit(() -> {
                busRouteRepository.save(busRoutes[index]);
//                log.info("Another Bus Route for {} has been created", busRoutes[index].getBus().getBusNo());
            });

        }

        routeExecutorService.shutdown();

        try {
            routeExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public void createAndSaveUsers() {

        ExecutorService userExecutorService = Executors.newFixedThreadPool(usernames.length);
        for(int i = 0; i < usernames.length; i++) {
            final int index = i;
            userExecutorService.submit(() -> {
                User user = new User(usernames[index], fullNames[index], passwordEncoder.encode(passwords[index]), emails[index], userAges[index], userGenders[index], phNos[index]);
                userRepository.save(user);

//                log.info("User {} has been created", user.getFullName());
            });

        }
    }

    @Transactional
    public void createAndSaveSeatCosts() {
        float sleeperWindowCost = 900.0f;
        float sleeperAisleCost = 700.0f;

        float acWindowCost = 600.0f;
        float acAisleCost = 400.0f;

        float nonAcWindowCost = 300.0f;
        float nonAcAisleCost = 100.0f;

        ExecutorService seatExecutorService = Executors.newFixedThreadPool(6);

        for(BusType busType :  BusType.values()) {
            for(SeatType seatType : SeatType.values()) {
                final BusType finalBusType = busType;
                final SeatType finalSeatType = seatType;
                seatExecutorService.submit(() -> {
                    float finalCost = switch (finalBusType) {
                        case SLEEPER -> (finalSeatType == SeatType.WINDOW) ? sleeperWindowCost : sleeperAisleCost;
                        case AC -> (finalSeatType == SeatType.WINDOW) ? acWindowCost : acAisleCost;
                        case NON_AC -> (finalSeatType == SeatType.WINDOW) ? nonAcWindowCost : nonAcAisleCost;
                    };

                    SeatCost seatCost = new SeatCost();
                    seatCost.setBusType(finalBusType);
                    seatCost.setSeatType(finalSeatType);
                    seatCost.setCost(finalCost);

                    seatCostRepository.save(seatCost);
                });

            }
        }

        seatExecutorService.shutdown();

        try {
            seatExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @Transactional
    public void createAndSaveBankDetails() {
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

        ExecutorService bankExecutorService = Executors.newFixedThreadPool(bankNames.length);

        for(int i = 0; i < bankNames.length; i++) {
            final int index = i;
            bankExecutorService.submit(() -> {
                BankDetails bankDetails = new BankDetails(bankNames[index], bankCodes[index]);
                bankDetailRepository.save(bankDetails);
            });

        }

        bankExecutorService.shutdown();

        try {
            bankExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public void createAndSaveBankAccounts() {

        long[] accountNos = {
                76845738593728L, 63748749393257L, 75839572749314L, 58349347583274L,
                89347598374584L, 94756382739275L, 75839284759348L, 47382947593857L,
                69238475293784L, 58392847539472L, 84927364582397L, 29384756294837L,
                57293847592385L, 72938475938475L, 93847592834758L, 47382958394729L,
                84572938475829L, 29583749384759L, 57294837458392L, 58374938472957L
        };

        double[] balances = {
                359309, 648384, 20000, 10000, 5638,
                55000, 26849, 17394, 32859, 60000,
                31847, 47295, 340000, 19428, 10424,
                52842, 40000, 24234, 12340, 42899

        };

        String[] ifscCodes = {
                "HDFC8459375",  // 0
                "AXIS4875392",  // 1
                "SBI4858363",   // 2
                "ICICI4759438", // 3
                "BOB6583947",  // 4
                "AXIS4305392",  // 5
                "CANARA6483926",  // 6
                "YES6273549",  // 7
                "PNB6483520",  // 8
                "ALHBD7305733",  // 9
                "HDFC8452075",  // 10
                "YES6483549",  // 11
                "AXIS4065392",  // 12
                "ICICI4399438",  // 13
                "PNB6482620",  // 14
                "SBI4831363",  // 15
                "BOB6545947",  // 16
                "ALHBD7495733",  // 17
                "GRMN6483925",  // 18
                "CANARA6419926",  // 19
        };

        String[] branchCodes = {
                "0348",
                "4620",
                "6205",
                "2493",
                "2940",
                "3240",
                "5720",
                "7402",
                "6205",
                "8401",
                "5920",
                "5927",
                "7284",
                "8193",
                "7283",
                "8294",
                "6284",
                "0947",
                "1874",
                "2749",
        };

        String[] branchNames = {
                "Andheri(East)",
                "Bandra",
                "Urla",
                "Borsi",
                "Jail Road",
                "Pendra",
                "Housing Board Colony",
                "Bemetara",
                "Boriwali",
                "Sindhiya Nagar",
                "BTM Layout Stage 2",
                "Bhilai-3",
                "Pachpedi Naka",
                "Dhaur",
                "Sector 6-A Market",
                "Kusumkasa",
                "Hudco",
                "Malviya Chowk",
                "Gudiyari",
                "Kurud",
        };

        String[] bankCodesPrefix = new String[]{
                "HDFC","AXIS","SBI","ICICI","BOB",
                "AXIS","CANARA","YES","PNB","ALHBD",
                "HDFC","YES","AXIS","ICICI","PNB",
                "SBI","BOB","ALHBD","GRMN","CANARA"
        };

        ExecutorService bankAccountExecutorService = Executors.newFixedThreadPool(accountNos.length);

        for(int i = 0; i < accountNos.length; i++) {
            final int index = i;
            bankAccountExecutorService.submit(() -> {

                BankDetails bankDetails = bankDetailRepository.findByBankCodeStartingWith(bankCodesPrefix[index]);
                BankAccount bankAccount = new BankAccount(
                        accountNos[index], balances[index], ifscCodes[index], branchCodes[index], branchNames[index]
                );
                bankAccount.setBankDetails(bankDetails);
                bankAccountRepository.save(bankAccount);
            });

        }

        bankAccountExecutorService.shutdown();

        try {
            bankAccountExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @Transactional
    public void assignUsersToBankAccounts() {
        List<String> ifscCodes = Arrays.asList(
                "HDFC8459375", "AXIS4305392", "PNB6482620", "CANARA6419926",
                "AXIS4875392", "CANARA6483926", "YES6483549", "BOB6545947",
                "SBI4858363", "YES6273549", "ICICI4399438", "ALHBD7495733",
                "ICICI4759438", "PNB6483520", "HDFC8452075", "GRMN6483925",
                "BOB6583947", "ALHBD7305733", "AXIS4065392", "SBI4831363"
        );

        // List of usernames for corresponding bank accounts
        List<String> userNames = Arrays.asList(
                "JohnDoe", "JohnDoe", "JohnDoe", "JohnDoe",
                "AliceSmith", "AliceSmith", "AliceSmith", "AliceSmith",
                "BobJohnson", "BobJohnson", "BobJohnson", "BobJohnson",
                "EmilyBrown", "EmilyBrown", "EmilyBrown", "EmilyBrown",
                "MichaelDavis", "MichaelDavis", "MichaelDavis", "MichaelDavis"
        );

        ExecutorService userAssigningExecutorService = Executors.newFixedThreadPool(ifscCodes.size());

        // Looping through the ifsc list and assign users based on them
        for (int i = 0; i < ifscCodes.size(); i++) {
            final int index = i;
            userAssigningExecutorService.submit(() -> {
                String ifsc = ifscCodes.get(index);
                String userName = userNames.get(index);

                // Fetching the bank account by IFSC
                BankAccount bankAccount = bankAccountRepository.findByIfscCode(ifsc);

                // Fetching the user by username
                User user = userRepository.findByUserName(userName);

                // Setting the user for the bank account
                bankAccount.setUser(user);

                bankAccountRepository.save(bankAccount);
            });
        }

        userAssigningExecutorService.shutdown();

        try {
            userAssigningExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @Transactional
    public void createAndSaveCardDetails() {
        String[] cardNumbers = {"4321111112345678", "1111234554324567", "5432111112346789", "6789432112345678"};
        String[] cardHolderNames = {
                "Vikram Bhatt",
                "Suraj Pancholi",
                "Anurag Basu",
                "Neeraj Pandey"
        };
        CardType[] cardTypes = {
                CardType.DEBIT,
                CardType.CREDIT,
                CardType.DEBIT,
                CardType.CREDIT
        };

        String[] cardCompanies = {
                "Visa",
                "MasterCard",
                "EasyShop",
                "Discover"
        };
        Byte[] expiryMonths = {11, 12, 8, 9};
        Integer[] expiryYears = {27, 29, 26, 25};
        String[] cVVs = {"123", "321", "456", "678"};

        List<User> users = userRepository.findAll();

        ExecutorService cardExecutorService = Executors.newFixedThreadPool(cardNumbers.length);

        for(int i = 0; i < cardNumbers.length; i++) {
            final int index = i;
            cardExecutorService.submit(() -> {
                CardDetails cardDetails;
                CardDTO cardDTO = paymentService.createCardDTO(cardNumbers[index], cardHolderNames[index], expiryMonths[index], expiryYears[index], cVVs[index], cardCompanies[index]);

                if(cardTypes[index] == CardType.DEBIT) {
                    List<BankAccount> bankAccounts = bankAccountRepository.findAll();
                    cardDetails = cardFactorySelector.createCard(cardDTO, CardType.DEBIT, bankAccounts);

                } else {
                    List<BankDetails> banks = bankDetailRepository.findAll();
                    cardDetails = cardFactorySelector.createCard(cardDTO, CardType.CREDIT, banks);

                }

                cardDetails.setLinkedUser(users.get(index));
                cardDetailRepository.save(cardDetails);
            });

        }

        cardExecutorService.shutdown();

        try {
            cardExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public void createAndSaveUserWallets() {
        List<User> users = userRepository.findAll();

        ExecutorService walletExecutorService = Executors.newFixedThreadPool(users.size());

        for(User user : users) {
            final User finalUser = user;
            walletExecutorService.submit(() -> {
                UserWallet uw = new UserWallet(BigDecimal.valueOf(2000));
                uw.setUser(finalUser);
                userWalletRepository.save(uw);
            });

        }

        walletExecutorService.shutdown();

        try {
            walletExecutorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public Booking createAndSaveBooking1(RandomSeatNumberProviderWithPreference rsnp) {
        log.info("Starting Booking 1");

        BusRoute busRouteForBooking1 = busRouteRepository.findByBus_BusNo("PJ02HK7295");

        Booking booking1 = new Booking(userRepository.findByUserName("EmilyBrown"), busRouteForBooking1, bookingDates[0], travelDates[0]);

        booking1.setUserPassenger(true);

        Bus busForBooking1 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking1).get(0);
        rsnp.setBusNo(busForBooking1.getBusNo());

        Float booking1Cost = 0.0f;
        int lastAssignedSeat = -1;

        if(booking1.isUserPassenger()) {
            BusSeat busSeatForUser = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking1, SeatType.AISLE, lastAssignedSeat);
            lastAssignedSeat = busSeatForUser.getSeatNumber();

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForUser, busSeatForUser.getSeatType());

            booking1Cost += seatCostForUser;

            createAndSaveUserPassengerToBooking(booking1, busSeatForUser);

        }

        BusSeat busSeat1 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking1, SeatType.WINDOW, lastAssignedSeat);
        lastAssignedSeat = busSeat1.getSeatNumber();

        BusSeat busSeat2 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking1, SeatType.WINDOW, lastAssignedSeat);

        BusType busTypeForSeat1 = busSeatRepository.findBusTypeFromBusSeat(busSeat1);
        Float seatCostForSeat1 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat1, busSeat1.getSeatType());

        BusType busTypeForSeat2 = busSeatRepository.findBusTypeFromBusSeat(busSeat2);
        Float seatCostForSeat2 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat2, busSeat2.getSeatType());


        booking1.addPassenger(new Passenger(passengerNames[0], passengerAges[0], passengerGenders[0], busSeat1));
        booking1.addPassenger(new Passenger(passengerNames[1], passengerAges[1], passengerGenders[1], busSeat2));

        booking1Cost = booking1Cost + seatCostForSeat1 + seatCostForSeat2;
        booking1.setPrice(booking1Cost);
        booking1.setBookingStatus(BookingStatus.CREATED);

        Booking bookingWithPaymentAdded = createAndSavePendingPaymentForBooking(booking1);

        busForBooking1.setAvailableSeats(busForBooking1.getAvailableSeats() -
                bookingWithPaymentAdded.getPassengers().size());
        busRepository.save(busForBooking1);

        log.info("Finishing Booking 1");

        return bookingRepository.save(bookingWithPaymentAdded);

    }

    public void createAndSaveUserPassengerToBooking(Booking booking, BusSeat busSeatForUser) {
        Passenger userPassenger = new Passenger();
        userPassenger.setName(booking.getUser().getFullName());
        userPassenger.setAge(booking.getUser().getAge());
        userPassenger.setGender(booking.getUser().getGender());
        userPassenger.setBusSeat(busSeatForUser);
        booking.addPassenger(userPassenger);
    }

    private BusSeat saveAssignedSeatToBusSeatEntityForBooking(RandomSeatNumberProviderWithPreference rsnp, Bus busForBooking, SeatType seatPref, Integer lastAssignedSeat) {
        int assignedSeat;
        if(seatPref == null) {
            assignedSeat = rsnp.getRandomSeatNumber(lastAssignedSeat, 2);

        } else {
            assignedSeat = rsnp.getRandomSeatNumberWithPreference(seatPref, true, lastAssignedSeat, 2);
        }

        BusSeat busSeat = new BusSeat();
        busSeat.setBus(busForBooking);
        busSeat.setSeatNumber(assignedSeat);
        busSeat.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeat));
        busSeat.setOccupied(true);
        busSeat.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        return busSeatRepository.save(busSeat);
    }

    @Transactional
    public Booking createAndSaveBooking2(RandomSeatNumberProviderWithPreference rsnp) {
        log.info("Starting Booking 2");
        BusRoute busRouteForBooking2 = busRouteRepository.findByBus_BusNo("AP11HL2756");

        User user = userRepository.findByUserName("BobJohnson");

        Booking booking2 = new Booking(user, busRouteForBooking2, bookingDates[1], travelDates[1]);

        Bus busForBooking2 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking2).get(0);
        rsnp.setBusNo(busForBooking2.getBusNo());

        BusSeat busSeat3 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking2, SeatType.AISLE, -1);

        BusType busTypeForSeat3 = busSeatRepository.findBusTypeFromBusSeat(busSeat3);
        Float seatCostForSeat3 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat3, busSeat3.getSeatType());


        booking2.addPassenger(new Passenger(passengerNames[2], passengerAges[2], passengerGenders[2], busSeat3));
        booking2.setPrice(seatCostForSeat3);
        booking2.setBookingStatus(BookingStatus.CREATED);

        // Doing payment for Booking 2 via User wallet
        WalletPaymentStrategy wps = new WalletPaymentStrategy(userWalletRepository);
        WalletPaymentParams walletPaymentParams = new WalletPaymentParams();
        walletPaymentParams.setUser(user);
        walletPaymentParams.setReceivedOtp(673412);
        walletPaymentParams.setPaymentDate(paymentDates[3]);
        booking2 = wps.processPayment(booking2, PaymentStatus.COMPLETED, walletPaymentParams);

        if(booking2 != null) {
            busForBooking2.setAvailableSeats(busForBooking2.getAvailableSeats() -
                    booking2.getPassengers().size());
            busRepository.save(busForBooking2);

            log.info("Finishing Booking 2");
            return bookingRepository.save(booking2);
        } else {
            log.info("Finishing Booking 2 null");
            return null;
        }

    }

    @Transactional
    public Booking createAndSaveBooking3(RandomSeatNumberProviderWithPreference rsnp) {
        log.info("Starting Booking 3");

        BusRoute busRouteForBooking3 = busRouteRepository.findByBus_BusNo("CG04LM7492");
        Booking booking3 = new Booking(userRepository.findByUserName("AliceSmith"), busRouteForBooking3, bookingDates[3], travelDates[3]);
        booking3.setUserPassenger(true);

        Bus busForBooking3 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking3).get(0);
        rsnp.setBusNo(busForBooking3.getBusNo());

        Float booking3Cost = 0.0f;

        int lastAssignedSeat = -1;

        if(booking3.isUserPassenger()) {

            BusSeat busSeatForUser = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking3, SeatType.WINDOW, -1);
            lastAssignedSeat = busSeatForUser.getSeatNumber();

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForUser, busSeatForUser.getSeatType());

            booking3Cost += seatCostForUser;

            createAndSaveUserPassengerToBooking(booking3, busSeatForUser);

        }

        BusSeat busSeat4 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking3, SeatType.AISLE, lastAssignedSeat);

        BusType busTypeForSeat4 = busSeatRepository.findBusTypeFromBusSeat(busSeat4);
        Float seatCostForSeat4 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat4, busSeat4.getSeatType());


        booking3.addPassenger(new Passenger(passengerNames[3], passengerAges[3], passengerGenders[3], busSeat4));

        booking3Cost = booking3Cost + seatCostForSeat4;
        booking3.setPrice(booking3Cost);
        booking3.setBookingStatus(BookingStatus.CREATED);

        CardPaymentStrategy cps = new CardPaymentStrategy(cardDetailRepository, bankAccountRepository);
        CardPaymentParams cardPaymentParams = new CardPaymentParams();
        cardPaymentParams.setLast4Digits(6789);
        cardPaymentParams.setPaymentDate(paymentDates[4]);
        cardPaymentParams.setReceivedOtp(840320);
        cardPaymentParams.setCardType(CardType.DEBIT);

        booking3 = cps.processPayment(booking3, PaymentStatus.COMPLETED, cardPaymentParams);

        if(booking3 != null) {
            busForBooking3.setAvailableSeats(busForBooking3.getAvailableSeats() -
                    booking3.getPassengers().size());
            busRepository.save(busForBooking3);

            log.info("Finishing Booking 3");

            return bookingRepository.save(booking3);
        } else {
            log.info("Finishing Booking 3 null");
            return null;
        }

    }

    @Transactional
    public Booking createAndSaveBooking4(RandomSeatNumberProviderWithPreference rsnp) {
        log.info("Starting Booking 4");
        BusRoute busRouteForBooking4 = busRouteRepository.findByBus_BusNo("PJ16TH1295");

        User user = userRepository.findByUserName("JohnDoe");


        Booking booking4 = new Booking(user, busRouteForBooking4, bookingDates[2], travelDates[2]);

        Bus busForBooking4 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking4).get(0);
        rsnp.setBusNo(busForBooking4.getBusNo());

        BusSeat busSeat5 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking4, SeatType.WINDOW, -1);

        BusType busTypeForSeat5 = busSeatRepository.findBusTypeFromBusSeat(busSeat5);
        Float seatCostForSeat5 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat5, busSeat5.getSeatType());


        booking4.addPassenger(new Passenger(passengerNames[4], passengerAges[4], passengerGenders[4], busSeat5));

        booking4.setPrice(seatCostForSeat5);
        booking4.setBookingStatus(BookingStatus.CREATED);

        NetbankingPaymentStrategy nbps = new NetbankingPaymentStrategy(bankDetailRepository, bankAccountRepository);
        NetbankingPaymentParams netbankingPaymentParams = new NetbankingPaymentParams();
        netbankingPaymentParams.setBankNamePrefix("HDFC");
        netbankingPaymentParams.setUserId(user.getUserId());
        netbankingPaymentParams.setReceivedOtp(343532);
        netbankingPaymentParams.setPaymentDate(paymentDates[2]);

        booking4 = nbps.processPayment(booking4, PaymentStatus.COMPLETED, netbankingPaymentParams);

        if(booking4 != null) {
            busForBooking4.setAvailableSeats(busForBooking4.getAvailableSeats() -
                    booking4.getPassengers().size());
            busRepository.save(busForBooking4);

            log.info("Finishing Booking 4");
            return bookingRepository.save(booking4);
        } else {
            log.info("Finishing Booking 4 null");
            return null;
        }
    }

    @Transactional
    public Booking createAndSaveBooking5(RandomSeatNumberProviderWithPreference rsnp) {

        log.info("Starting Booking 5");
        BusRoute busRouteForBooking5 = busRouteRepository.findByBus_BusNo("PJ02BL7215");

        Booking booking5 = new Booking(userRepository.findByUserName("BobJohnson"), busRouteForBooking5, bookingDates[4], travelDates[4]);

        Bus busForBooking5 = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking5).get(0);
        rsnp.setBusNo(busForBooking5.getBusNo());

        BusSeat busSeat6 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking5, null, -1);

        BusType busTypeForSeat6 = busSeatRepository.findBusTypeFromBusSeat(busSeat6);
        Float seatCostForSeat6 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat6, busSeat6.getSeatType());

        booking5.addPassenger(new Passenger(passengerNames[5], passengerAges[5], passengerGenders[5], busSeat6));

        booking5.setPrice(seatCostForSeat6);
        booking5.setBookingStatus(BookingStatus.CREATED);

        CardPaymentStrategy cps = new CardPaymentStrategy(cardDetailRepository, bankAccountRepository);
        CardPaymentParams cardPaymentParams = new CardPaymentParams();
        cardPaymentParams.setLast4Digits(5678);
        cardPaymentParams.setPaymentDate(paymentDates[4]);
        cardPaymentParams.setReceivedOtp(457433);
        cardPaymentParams.setCardType(CardType.CREDIT);

        booking5 = cps.processPayment(booking5, PaymentStatus.FAILED, cardPaymentParams);

        if(booking5 != null) {
            busForBooking5.setAvailableSeats(busForBooking5.getAvailableSeats() -
                    booking5.getPassengers().size());
            busRepository.save(busForBooking5);

            log.info("Finishing Booking 5");

            return bookingRepository.save(booking5);

        } else {
            log.info("Finishing Booking 5 null");
            return null;

        }


    }

    @Transactional
    public Booking createAndSaveBooking6(RandomSeatNumberProviderWithPreference rsnp) {
        log.info("Starting booking 6");
        BusRoute busRouteForBooking = busRouteRepository.findByBus_BusNo("MH04PW2747");

        Booking booking = new Booking(userRepository.findByUserName("MichaelDavis"), busRouteForBooking, bookingDates[5], travelDates[5]);

        booking.setUserPassenger(true);

        Bus busForBooking = busRouteRepository.findBusesAvailableInGivenBusRoute(busRouteForBooking).get(0);
        rsnp.setBusNo(busForBooking.getBusNo());

        Float bookingCost = 0.0f;
        int lastAssignedSeat = -1;

        if(booking.isUserPassenger()) {
            BusSeat busSeatForUser = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking, SeatType.AISLE, lastAssignedSeat);
            lastAssignedSeat = busSeatForUser.getSeatNumber();

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForUser, busSeatForUser.getSeatType());

            bookingCost += seatCostForUser;

            createAndSaveUserPassengerToBooking(booking, busSeatForUser);

        }

        BusSeat busSeat1 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking, SeatType.WINDOW, lastAssignedSeat);
        lastAssignedSeat = busSeat1.getSeatNumber();

        BusSeat busSeat2 = saveAssignedSeatToBusSeatEntityForBooking(rsnp, busForBooking, SeatType.WINDOW, lastAssignedSeat);

        BusType busTypeForSeat1 = busSeatRepository.findBusTypeFromBusSeat(busSeat1);
        Float seatCostForSeat1 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat1, busSeat1.getSeatType());

        BusType busTypeForSeat2 = busSeatRepository.findBusTypeFromBusSeat(busSeat2);
        Float seatCostForSeat2 = seatCostRepository.findSeatCostByBusTypeAndSeatType(busTypeForSeat2, busSeat2.getSeatType());


        booking.addPassenger(new Passenger(passengerNames[7], passengerAges[7], passengerGenders[7], busSeat1));
        booking.addPassenger(new Passenger(passengerNames[8], passengerAges[8], passengerGenders[8], busSeat2));

        bookingCost = bookingCost + seatCostForSeat1 + seatCostForSeat2;
        booking.setPrice(bookingCost);
        booking.setBookingStatus(BookingStatus.CREATED);

        Booking bookingWithPaymentAdded = createAndSavePendingPaymentForBooking(booking);

        busForBooking.setAvailableSeats(busForBooking.getAvailableSeats() -
                bookingWithPaymentAdded.getPassengers().size());
        busRepository.save(busForBooking);

        log.info("Finishing Booking 6");

        return bookingRepository.save(bookingWithPaymentAdded);

    }

    private Booking createAndSavePendingPaymentForBooking(Booking booking) {
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);
        log.info("Payment pending: {} {}", booking.getPrice(), booking.getTravelDate());

        payment.setPaymentMethod(null);
        payment.setAmount(0.00f);
        payment.setPaymentDate(null);
        payment.setBooking(booking);
        booking.setPayment(payment);

        return booking;
    }

    @Transactional
    public void markingTravelForBooking(Booking booking) {
        log.info("Starting marking booking : {}", booking.getBookingId());
        List<Passenger> listOfPassengersInB1 = bookingRepository.findAllPassengersInBooking(booking);
        List<Passenger> travellingPassengersB1 = new ArrayList<>();
        travellingPassengersB1.add(listOfPassengersInB1.get(0));

        ExecutorService traveledMarkService = Executors.newFixedThreadPool(listOfPassengersInB1.size());

        for(Passenger p : listOfPassengersInB1) {
            final Passenger finalP = p;
            traveledMarkService.submit(() -> finalP.setTraveled(travellingPassengersB1.contains(finalP)));

        }

        traveledMarkService.shutdown();

        try {
            traveledMarkService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Updating all passenger's travel status belonging to a booking
        passengerRepository.saveAll(listOfPassengersInB1);

        List<Passenger> passengersInBooking = bookingRepository.findAllPassengersInBooking(booking);

        ExecutorService busSeatService = Executors.newFixedThreadPool(passengersInBooking.size());

        for(Passenger p : passengersInBooking) {
            final Passenger finalP = p;
            busSeatService.submit(() -> {
                BusSeat busSeat = finalP.getBusSeat();
                busSeat.setOccupied(false);
                busSeatRepository.save(busSeat);
            });

        }

        busSeatService.shutdown();

        try {
            busSeatService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Bus busForBooking = bookingRepository.findBusForBooking(booking.getBookingId());

        busForBooking.setAvailableSeats(busForBooking.getAvailableSeats() + passengersInBooking.size());
        busRepository.save(busForBooking);

        log.info("Finishing marking booking : {}", booking.getBookingId());
    }

}
