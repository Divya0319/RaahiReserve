package com.fastturtle.redbusschemadesign.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookingCancellationService {

    @Scheduled(initialDelay = 0, fixedRate = 15000)
    private void checkAndCancelUnpaidBookings() {
        System.out.println("Checking unpaid bookings");
        System.out.println("Booking cancellation done");
    }

}
