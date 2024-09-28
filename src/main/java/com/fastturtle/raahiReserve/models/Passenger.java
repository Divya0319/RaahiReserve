package com.fastturtle.raahiReserve.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastturtle.raahiReserve.enums.Gender;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengerId")
    private Integer passengerId;

    @ManyToMany(mappedBy = "passengers")
    @JsonIgnore
    private Set<Booking> bookings;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "bus_seat_id")
    private BusSeat busSeat;

    @Column(name = "traveled")
    private boolean traveled = false;

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

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.getPassengers().add(this);
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

    public Boolean isTraveled() {
        return traveled;
    }

    public void setTraveled(Boolean traveled) {
        this.traveled = traveled;
    }
}
