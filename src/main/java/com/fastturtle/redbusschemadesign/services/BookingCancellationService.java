package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingCancellationService {

    private final Logger logger = LoggerFactory.getLogger(BookingCancellationService.class);

    private final BookingService bookingService;

    @Autowired
    public BookingCancellationService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(initialDelay = 0, fixedDelay = 30000)
    private void checkAndCancelUnpaidBookings() {
        List<Booking> bookings = bookingService.getBookingsWithinNext48HoursWithPendingOrFailedPayment();
        logger.info("Cancelling unpaid bookings");
        for (Booking booking : bookings) {
            logger.info("Booking details : {}", booking);
        }
    }

}
