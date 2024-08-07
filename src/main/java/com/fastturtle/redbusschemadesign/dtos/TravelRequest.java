package com.fastturtle.redbusschemadesign.dtos;

import com.fastturtle.redbusschemadesign.models.Passenger;

import java.util.Set;

public class TravelRequest {

    private int bookingId;
    private Set<Passenger> passengers;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }
}
