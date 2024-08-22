package com.fastturtle.redbusschemadesign.models;

import jakarta.persistence.*;

@Entity
@Table(name = "busSeat", uniqueConstraints =
@UniqueConstraint(columnNames = {"bus_id", "seat_number"}))
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "busSeatId")
    private int busSeatId;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatType")   // allow null for "NO_PREFERENCE"
    private SeatType seatType;

    @Column(name = "occupied")
    private boolean occupied = false;

    public int getBusSeatId() {
        return busSeatId;
    }

    public void setBusSeatId(int busSeatId) {
        this.busSeatId = busSeatId;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
