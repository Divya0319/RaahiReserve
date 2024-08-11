package com.fastturtle.redbusschemadesign;

import com.fastturtle.redbusschemadesign.models.BusType;
import com.fastturtle.redbusschemadesign.models.PaymentMethods;
import com.fastturtle.redbusschemadesign.models.PaymentStatus;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleDataInitializer {
    private BusRepository busRepository;

    @Autowired
    public SampleDataInitializer(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @PostConstruct
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

        String[] usernames = {"JohnDoe", "AliceSmith", "BobJohnson", "EmilyBrown", "MichaelDavis"};
        String[] password = {"doe@123", "smith@234", "john@345", "brown@456", "davis@567"};
        String[] emails = {"john.doe@example.com", "alice.smith@example.com", "bob.johnson@example.com", "emily.brown@example.com",
                "michael.davis@example.com"};
        String[] phNos = {"9898976767", "7878765656", "8989877665", "9988656543", "8987967578"};




        // Sample data for Designer
        String[] firstNamesDes = {"Sarah", "David", "Emma", "Ryan", "Olivia"};
        String[] lastNamesDes = {"Wilson", "Jones", "Taylor", "Clark", "Anderson"};
        String[] emailsDes = {
                "sarah.wilson@example.com",
                "david.jones@example.com",
                "emma.taylor@example.com",
                "ryan.clark@example.com",
                "olivia.anderson@example.com"
        };

        String[] bookingDates = {"02/09/2023", "08/09/2023", "10/09/2023", "12/12/2023", "08/08/2024"};
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


        // Sample data for Book
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

        String[] passengerGenders = {
                "FEMALE", // Alice Johnson
                "MALE",   // Bob Smith
                "OTHER",  // Charlie Brown
                "MALE",   // David Wilson
                "FEMALE", // Eva Davis
                "MALE",   // Frank Moore
                "OTHER",  // Grace Taylor
                "FEMALE", // Hannah Anderson
                "MALE",   // Isaac Thomas
                "OTHER"   // Julia Martin
        };

        // TODO : assign bus seat to a passenger randomly from available seats, and if that seat is already booked, do another random search
        // TODO: give option to choose aisle or window, depending on availability, allot them that seat

        // TODO: entry for bus seat booking will be done in bus_seat table, and it will hold only those entries of seats which are booked
        // TODO: once travel is done for a booking, vacate that seat from the bus(seems logical)

        // TODO: When a passenger confirms his travel, mark traveled = true for that booking id( need to rethink it)


        // Sample data for BookReview
        String[] comments = {
                "Great book, highly recommended.",
                "Interesting read, but could use more examples.",
                "Loved it! Can't wait for the sequel.",
                "Informative and well-written.",
                "Not what I expected, but pleasantly surprised."
        };


        for (int i = 0; i < 5; i++) {
            // Create and save CoderDetail


            // Create and save Coder



        }

    }


}
