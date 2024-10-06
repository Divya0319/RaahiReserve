package com.fastturtle.raahiReserve.helpers;

import com.fastturtle.raahiReserve.enums.SeatType;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;

import java.util.List;
import java.util.Random;

public class RandomSeatNumberProviderWithPreference extends RandomSeatNumberProvider {

    private static final int[] SEAT_PATTERN = {1, 2, 2, 1};  // 1 - Window, 2 - Aisle

    public RandomSeatNumberProviderWithPreference(BusRepository busRepository, BusSeatRepository busSeatRepository) {
        super(busRepository, busSeatRepository);
    }

    public int getRandomSeatNumberWithPreference(SeatType preference,
                                                 boolean assignSeatIfPreferenceUnavailable, int lastAssignedSeat, int maxDistance) {
        List<Integer> availableSeats = getAvailableSeats();

        if(lastAssignedSeat != -1) {
            // Trying to assign adjacent or nearby seats
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

        // First passenger or no adjacent seats available, assigning based on preference
        List<Integer> preferredSeats = availableSeats.stream()
                .filter(seat -> seatMatchesPreference(seat, preference))
                .toList();

        if(!preferredSeats.isEmpty()) {
            return getRandomSeatFromList(preferredSeats);
        } else if(!availableSeats.isEmpty() && assignSeatIfPreferenceUnavailable) {
            return getRandomSeatFromList(availableSeats);
        } else {
            return -1;
        }
    }

    private boolean seatMatchesPreference(int seatNumber, SeatType preference) {
        int position = (seatNumber - 1) % SEAT_PATTERN.length;
        SeatType seatType = SEAT_PATTERN[position] == 1 ? SeatType.WINDOW : SeatType.AISLE;
        return seatType == preference;
    }

    public SeatType getSeatTypeFromSeatNumber(int seatNumber) {
        int position = (seatNumber - 1) % SEAT_PATTERN.length;
        return SEAT_PATTERN[position] == 1 ? SeatType.WINDOW : SeatType.AISLE;
    }

    private int getRandomSeatFromList(List<Integer> seats) {
        Random random = new Random();
        int randomIndex = random.nextInt(seats.size());
        return seats.get(randomIndex);
    }
}
