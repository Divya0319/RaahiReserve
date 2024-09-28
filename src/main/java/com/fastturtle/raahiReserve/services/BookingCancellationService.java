package com.fastturtle.raahiReserve.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingCancellationService {

    private final Logger logger = LoggerFactory.getLogger(BookingCancellationService.class);

    private final BookingService bookingService;

    @Autowired
    public BookingCancellationService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

//    @Scheduled(initialDelay = 0, fixedDelay = 30000)
//    private void checkAndCancelUnpaidBookings() {
//        List<Booking> bookings = bookingService.getBookingsWithinNext48HoursWithPendingOrFailedPayment();
//        logger.info("Cancelling unpaid bookings");
//        for (Booking booking : bookings) {
//            logger.info("Booking details : {}", booking);
//
////            booking.setBookingStatus(BookingStatus.CANCELLED);
////            logger.info("Booking {} is cancelled now", booking.getBookingId());
//        }
//    }

}
