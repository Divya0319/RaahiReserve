package com.fastturtle.redbusschemadesign.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BookingCancellationService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startBookingCheckTask() {
        scheduler.scheduleAtFixedRate(this::checkAndCancelUnpaidBookings, 1, 1, TimeUnit.MINUTES);
    }

    private void checkAndCancelUnpaidBookings() {
        System.out.println("Checking unpaid bookings");
        System.out.println("Booking cancellation done");
    }

}
