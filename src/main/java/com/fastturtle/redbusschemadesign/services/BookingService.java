package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.BusRouteRepository;
import com.fastturtle.redbusschemadesign.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public ResponseEntity<?> bookBus(BookingRequest bookingRequest) {
        BusRoute busRoute = busRouteRepository.findFirstBusRouteBySourceAndDestination(bookingRequest.getSource(), bookingRequest.getDestination());

        if(busRoute == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Bus route not found"));
        }


        Optional<User> optionalUser = userRepository.findById(bookingRequest.getUserId());
        User user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Bus bus = busRoute.getBus();
        ResponseEntity<?> response;
        if (bus.getAvailableSeats() < bookingRequest.getPassengerCount()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Not enough available seats");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else {
            bus.setAvailableSeats(bus.getAvailableSeats() - bookingRequest.getPassengerCount());
            busRepository.save(bus);

            Booking booking = new Booking();
            booking.setBusRoute(busRoute);
            booking.setUser(user);
            booking.setBookingDate(LocalDate.now());
            response = ResponseEntity.ok(booking);
        }

        return response;
    }

    public ResponseEntity<?> getAverageCostOfTicketsOnDate(LocalDate date) {
        Double averageCost = bookingRepository.findAverageCostOfTicketsOnDate(date);
        ResponseEntity<?> response;
        if(averageCost == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No bookings for given date"));
        } else {
            response = ResponseEntity.ok(averageCost);
        }
        return response;
    }

    public ResponseEntity<?> fetchAllPassengersForBooking(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);

        ResponseEntity<?> response;
        if(optionalBooking.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        } else {
           Booking booking = optionalBooking.get();
           List<Passenger> passengers = booking.getPassengers();


           response = ResponseEntity.ok(passengers);
        }

        return response;
    }
}
