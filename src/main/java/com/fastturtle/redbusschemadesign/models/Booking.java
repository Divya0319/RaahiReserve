package com.fastturtle.redbusschemadesign.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bus_route_id")
    private BusRoute busRoute;

    @Column(name = "bookingDate")
    private LocalDate bookingDate;

    @Column(name = "travelDate")
    private LocalDate travelDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
            (name = "booking_passenger",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    @JsonIgnore
    private List<Passenger> passengers;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @Column(name = "is_user_passenger")
    private boolean isUserPassenger = false;

    @Column(name = "price")
    private float price;

    @Transient
    private String formattedBookingDate;

    @Transient
    private String formattedTravelDate;

    public Booking(User user, BusRoute busRoute, LocalDate bookingDate, LocalDate travelDate) {
        this.user = user;
        this.busRoute = busRoute;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
        this.passengers = new ArrayList<>();
    }

    public Booking() {
        this.passengers = new ArrayList<>();
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BusRoute getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.getBookings().add(this);
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isUserPassenger() {
        return isUserPassenger;
    }

    public void setUserPassenger(boolean userPassenger) {
        isUserPassenger = userPassenger;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getFormattedBookingDate() {
        return formattedBookingDate;
    }

    public void setFormattedBookingDate(String formattedBookingDate) {
        this.formattedBookingDate = formattedBookingDate;
    }

    public String getFormattedTravelDate() {
        return formattedTravelDate;
    }

    public void setFormattedTravelDate(String formattedTravelDate) {
        this.formattedTravelDate = formattedTravelDate;
    }
}
