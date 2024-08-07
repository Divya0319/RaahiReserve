package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Passenger;
import com.fastturtle.redbusschemadesign.models.Travel;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.PassengerRepository;
import com.fastturtle.redbusschemadesign.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
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

    public Travel travel(TravelRequest travelRequest) {
        bookingRepository.findById(travelRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Travel travel = new Travel();
        travel.setTraveled(true);
        Set<Passenger> passengers = new HashSet<>();

        for(Integer passengerId : travelRequest.getPassengerIds()) {
            Optional<Passenger> passenger = passengerRepository.findById(passengerId);
            passenger.ifPresent(passengers::add);
        }

        if(!passengers.isEmpty()) {
            travel.setPassengers(passengers);
        }

        return travelRepository.save(travel);
    }

    public long getPassengersTraveledOnDate(LocalDate date) {
        return travelRepository.countByTraveledDate(date);
    }
}
