package com.fastturtle.redbusschemadesign.dtos;

import com.fastturtle.redbusschemadesign.models.Passenger;

import java.util.List;
import java.util.Set;

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
