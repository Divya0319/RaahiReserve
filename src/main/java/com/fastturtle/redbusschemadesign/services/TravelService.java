package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Travel;
import com.fastturtle.redbusschemadesign.repositories.BookingRepository;
import com.fastturtle.redbusschemadesign.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TravelService {

    private final TravelRepository travelRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository, BookingRepository bookingRepository) {
        this.travelRepository = travelRepository;
        this.bookingRepository = bookingRepository;
    }

    public Travel travel(TravelRequest travelRequest) {
        Booking booking = bookingRepository.findById(travelRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Travel travel = new Travel();
        travel.setTraveled(true);
        travel.setPassengers(travelRequest.getPassengers());

        return travelRepository.save(travel);
    }

    public long getPassengersTraveledOnDate(LocalDate date) {
        return travelRepository.countByTraveledDate(date);
    }
}
