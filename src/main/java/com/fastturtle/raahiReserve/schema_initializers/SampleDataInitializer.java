package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;
import com.fastturtle.raahiReserve.services.InitialDataService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        ExecutorService executor = Executors.newFixedThreadPool(12);

        Future<?> initialTask1 = executor.submit(() -> {
            initialDataService.createAndSaveBusesAndBusRoutes();
            initialDataService.createAndInsert10MoreBuses();
        });

        Future<?> initialTask2 = executor.submit(initialDataService::createAndSaveUsers);

        Future<?> initialTask3 = executor.submit(initialDataService::createAndSaveSeatCosts);

        Future<?> initialTask4 = executor.submit(() -> {
            initialDataService.createAndSaveBankDetails();
            initialDataService.createAndSaveBankAccounts();
            initialDataService.createAndSaveCardDetails();
        });

        Future<?> initialTask5 = executor.submit(initialDataService::createAndSaveUserWallets);

        try {

            initialTask1.get();
            initialTask2.get();
            initialTask3.get();
            initialTask4.get();
            initialTask5.get();

            RandomSeatNumberProviderWithPreference rsnp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);

            Future<Booking> bookingFuture1 = executor.submit(() -> initialDataService.createAndSaveBooking1(rsnp));
            Future<Booking> bookingFuture2 = executor.submit(() -> initialDataService.createAndSaveBooking2(rsnp));
            Future<Booking> bookingFuture3 = executor.submit(() -> initialDataService.createAndSaveBooking3(rsnp));
            Future<Booking> bookingFuture4 = executor.submit(() -> initialDataService.createAndSaveBooking4(rsnp));
            Future<Booking> bookingFuture5 = executor.submit(() -> initialDataService.createAndSaveBooking5(rsnp));
            executor.submit(() -> initialDataService.createAndSaveBooking6(rsnp));

            Booking booking1 = bookingFuture1.get();
            Booking booking2 = bookingFuture2.get();
            Booking booking3 = bookingFuture3.get();
            Booking booking4 = bookingFuture4.get();
            Booking booking5 = bookingFuture5.get();

            executor.submit(() -> initialDataService.markingTravelForBooking(booking1));
            executor.submit(() -> initialDataService.markingTravelForBooking(booking2));
            executor.submit(() -> initialDataService.markingTravelForBooking(booking3));
            executor.submit(() -> initialDataService.markingTravelForBooking(booking4));
            executor.submit(() -> initialDataService.markingTravelForBooking(booking5));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }


}
