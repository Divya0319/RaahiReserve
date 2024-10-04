package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.models.Booking;
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

        Future<Booking> bookingFuture1 = bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking1(rsnp));
        Future<Booking> bookingFuture2 = bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking2(rsnp));
        Future<Booking> bookingFuture3 = bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking3(rsnp));
        Future<Booking> bookingFuture4 = bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking4(rsnp));
        Future<Booking> bookingFuture5 = bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking5(rsnp));
        bookingsExecutorService.submit(() -> initialDataService.createAndSaveBooking6(rsnp));

        try {
            Booking booking1 = bookingFuture1.get();
            initialDataService.markingTravelForBooking(booking1);

            Booking booking2 = bookingFuture2.get();
            initialDataService.markingTravelForBooking(booking2);

            Booking booking3 = bookingFuture3.get();
            initialDataService.markingTravelForBooking(booking3);

            Booking booking4 = bookingFuture4.get();
            initialDataService.markingTravelForBooking(booking4);

            Booking booking5 = bookingFuture5.get();
            initialDataService.markingTravelForBooking(booking5);

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }

        bookingsExecutorService.shutdown();

    }

}
