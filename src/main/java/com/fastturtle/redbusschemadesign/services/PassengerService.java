package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.Passenger;
import com.fastturtle.redbusschemadesign.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public void saveAllPassengers(List<Passenger> passergers) {
        passengerRepository.saveAll(passergers);
    }

    public List<Passenger> findAllPassengersById(List<Integer> ids) {
        return passengerRepository.findAllById(ids);
    }
}
