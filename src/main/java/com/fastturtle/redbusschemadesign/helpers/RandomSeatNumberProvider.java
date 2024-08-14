package com.fastturtle.redbusschemadesign.helpers;

import com.fastturtle.redbusschemadesign.repositories.BusRepository;
import com.fastturtle.redbusschemadesign.repositories.BusSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class RandomSeatNumberProvider {

    private String busNo;
    private BusRepository busRepository;

    private BusSeatRepository busSeatRepository;


    @Autowired
    public RandomSeatNumberProvider(BusRepository busRepository, BusSeatRepository busSeatRepository) {
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
    }

    public RandomSeatNumberProvider() {

    }

    public List<Integer> getAvailableSeats() {
        int totalSeats = busRepository.findTotalSeatsByBusNo(busNo);

        List<Integer> bookedSeats = busSeatRepository.findAvailableSeatsByBusNo(busNo);

        List<Integer> allSeats = IntStream.rangeClosed(1, totalSeats)
                .boxed()
                .toList();

        return allSeats.stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .toList();
    }

    public int getRandomSeatNumber() {
        List<Integer> availableSeats = getAvailableSeats();

        if(availableSeats.isEmpty()) {
            throw new RuntimeException("No available seats");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(availableSeats.size());

        return availableSeats.get(randomIndex);

    }


    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
}
