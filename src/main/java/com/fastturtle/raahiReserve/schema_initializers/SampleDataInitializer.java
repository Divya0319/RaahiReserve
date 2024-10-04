package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;
import com.fastturtle.raahiReserve.services.InitialDataService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class SampleDataInitializer {

    private final InitialDataService initialDataService;
    private final BusRepository busRepository;
    private final BusSeatRepository busSeatRepository;


    @Autowired
    public SampleDataInitializer(InitialDataService initialDataService, BusRepository busRepository, BusSeatRepository busSeatRepository) {
        this.initialDataService = initialDataService;
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
    }

    @PostConstruct
    public void init() {

        initialDataService.createAndSaveBusesAndBusRoutes();
        initialDataService.createAndInsert10MoreBuses();
        initialDataService.createAndSaveUsers();
        initialDataService.createAndSaveSeatCosts();
        initialDataService.createAndSaveBankDetails();
        initialDataService.createAndSaveBankAccounts();
        initialDataService.assignUsersToBankAccounts();
        initialDataService.createAndSaveCardDetails();
        initialDataService.createAndSaveUserWallets();

        RandomSeatNumberProviderWithPreference rsnp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);

        ExecutorService bookingsExecutorService = Executors.newFixedThreadPool(6);

        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking1(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking2(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking3(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking4(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking5(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking6(rsnp));

        bookingsExecutorService.shutdown();


    }

}
