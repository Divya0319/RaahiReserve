package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Passenger;
import com.fastturtle.redbusschemadesign.models.Travel;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.PassengerRepository;
import com.fastturtle.redbusschemadesign.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TravelService {

    private final TravelRepository travelRepository;

    private final BookingRepository bookingRepository;

    private final PassengerRepository passengerRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository) {
        this.travelRepository = travelRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
    }

    public long getPassengersTraveledOnDate(LocalDate date) {
        return travelRepository.countByTraveledDate(date);
    }

    public ResponseEntity<?> updateTravelStatus(TravelRequest travelRequest) {
        Optional<Booking> booking = bookingRepository.findById(travelRequest.getBookingId());
        if (booking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }

        Travel travel = travelRepository.findTravelByBookingId(travelRequest.getBookingId());
        if(travel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Travel not found"));
        }
        Set<Passenger> passengers = new HashSet<>();

        for(Integer passengerId : travelRequest.getPassengerIds()) {
            Optional<Passenger> passenger = passengerRepository.findById(passengerId);
            passenger.ifPresent(passengers::add);
        }

        for(Passenger p : passengers) {
            p.setTraveled(true);
            travel.addPassenger(p);
        }

        travel.setBooking(booking.get());   // Ensure the travel is linked to the booking

        travel.setTravelDate(LocalDate.now());

        return ResponseEntity.ok().body(travelRepository.save(travel));
    }
}
