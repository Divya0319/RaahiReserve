package com.fastturtle.raahiReserve.helpers;

import com.fastturtle.raahiReserve.enums.SeatType;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class RandomSeatNumberProvider {

    private static final int[] SEAT_PATTERN = {1, 2, 2, 1};  // 1 - Window, 2 - Aisle

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

        List<Integer> bookedSeats = busSeatRepository.findBookedSeatsByBusNo(busNo);

        List<Integer> allSeats = IntStream.rangeClosed(1, totalSeats)
                .boxed()
                .toList();

        return allSeats.stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .toList();
    }

    public int getRandomSeatNumber(int lastAssignedSeat, int maxDistance) {
        List<Integer> availableSeats = getAvailableSeats();

        int assignedSeat = -1;

        if(availableSeats.isEmpty()) {
            throw new RuntimeException("No available seats");
        }

        if(lastAssignedSeat == -1) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableSeats.size());
            return availableSeats.get(randomIndex);
        } else {
            for(int offset = 1; offset <= maxDistance; offset++) {
                int seatBefore = lastAssignedSeat - offset;
                int seatAfter = lastAssignedSeat + offset;

                if(availableSeats.contains(seatAfter)) {
                    return seatAfter;
                } else if(availableSeats.contains(seatBefore)) {
                    return seatBefore;
                }
            }
        }

        return assignedSeat;

    }

    public SeatType getSeatTypeFromSeatNumber(int seatNumber) {
        int position = (seatNumber - 1) % SEAT_PATTERN.length;
        return SEAT_PATTERN[position] == 1 ? SeatType.WINDOW : SeatType.AISLE;
    }


    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
}
