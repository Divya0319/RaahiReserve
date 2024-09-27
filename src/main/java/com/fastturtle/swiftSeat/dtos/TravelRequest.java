package com.fastturtle.swiftSeat.dtos;

import java.util.List;

public class TravelRequest {

    private int bookingId;
    private List<Integer> passengerIds;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public List<Integer> getPassengerIds() {
        return passengerIds;
    }

    public void setPassengerIds(List<Integer> passengerIds) {
        this.passengerIds = passengerIds;
    }
}
