package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.models.BusRoute;
import com.fastturtle.redbusschemadesign.models.User;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRouteRepository;
import com.fastturtle.redbusschemadesign.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BusRouteRepository busRouteRepository;

    private final UserRepository userRepository;

    private final BusRepository busRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusRepository busRepository) {
        this.bookingRepository = bookingRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busRepository = busRepository;
    }

    public Booking bookBus(BookingRequest bookingRequest) {
        BusRoute busRoute = busRouteRepository.findById(bookingRequest.getBusRouteId())
                .orElseThrow(() -> new RuntimeException("BusRoute not found"));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bus bus = busRoute.getBus();

        if (bus.getAvailableSeats() < bookingRequest.getPassengerCount()) {
            throw new RuntimeException("Not enough available seats");
        }

        bus.setAvailableSeats(bus.getAvailableSeats() - bookingRequest.getPassengerCount());
        busRepository.save(bus);

        Booking booking = new Booking();
        booking.setBusRoute(busRoute);
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());

        return bookingRepository.save(booking);
    }
}
