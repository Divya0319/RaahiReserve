package com.fastturtle.redbusschemadesign.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookingCancellationService {

    private final Logger logger = LoggerFactory.getLogger(BookingCancellationService.class);

    @Scheduled(initialDelay = 0, fixedRate = 15000)
    private void checkAndCancelUnpaidBookings() {
        logger.info("Checking unpaid bookings");
        logger.info("Booking cancellation done");
    }

}
