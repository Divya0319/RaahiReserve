package com.fastturtle.redbusschemadesign.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengerId")
    private int passengerId;

    @ManyToMany(mappedBy = "passengers")
    @JsonIgnore
    private Set<Booking> bookings;

    @ManyToMany(mappedBy = "passengers")
    @JsonIgnore
    private Set<Travel> travels;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "bus_seat_id")
    private BusSeat busSeat;

    public Passenger(String name, int age, Gender gender, BusSeat busSeat) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.busSeat = busSeat;
        this.bookings = new HashSet<>();
    }

    public Passenger() {
        this.bookings = new HashSet<>();
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.getPassengers().add(this);
    }

    public Set<Travel> getTravels() {
        return travels;
    }

    public void setTravels(Set<Travel> travels) {
        this.travels = travels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BusSeat getBusSeat() {
        return busSeat;
    }

    public void setBusSeat(BusSeat busSeat) {
        this.busSeat = busSeat;
    }
}
