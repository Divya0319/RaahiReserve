package com.fastturtle.redbusschemadesign.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelId")
    private int travelId;

    @ManyToMany
    @JoinTable(
            name = "passenger_travel",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    @JsonIgnore
    private List<Passenger> passengers;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "travelDate")
    private LocalDate travelDate;

    public Travel() {
        this.passengers = new ArrayList<>();
    }

    public int getTravelId() {
        return travelId;
    }

    public void setTravelId(int travelId) {
        this.travelId = travelId;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
