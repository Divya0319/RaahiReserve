package com.fastturtle.swiftSeat.services;

import com.fastturtle.swiftSeat.models.Booking;
import com.fastturtle.swiftSeat.models.Bus;
import com.fastturtle.swiftSeat.models.Passenger;
import com.fastturtle.swiftSeat.repositories.BusRepository;
import com.fastturtle.swiftSeat.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final BusRepository busRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, BusRepository busRepository) {
        this.passengerRepository = passengerRepository;
        this.busRepository = busRepository;
    }

    public void saveAllPassengers(List<Passenger> passengers) {
        passengerRepository.saveAll(passengers);
    }

    public List<Passenger> findAllPassengersById(List<Integer> ids) {
        return passengerRepository.findAllById(ids);
    }

    public void markPassengersAsTraveled(Booking booking, List<Integer> passengerIds) {

        List<Passenger> passengers = findAllPassengersById(passengerIds);
        for (Passenger passenger : passengers) {
            passenger.setTraveled(true);
        }
        saveAllPassengers(passengers);

        if(booking != null) {
            // Vacating seats of passengers
            Bus busForBooking = booking.getBusRoute().getBus();
            int availableSeats = busForBooking.getAvailableSeats();
            int vacatingSeats = passengers.size();
            busForBooking.setAvailableSeats(availableSeats + vacatingSeats);
            busRepository.save(busForBooking);
        }
    }
}
